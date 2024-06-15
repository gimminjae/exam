package com.example.kotlinback.member.service;

import com.example.kotlinback.common.jwt.provider.JwtProvider;
import com.example.kotlinback.common.mail.dto.MailTo;
import com.example.kotlinback.common.mail.service.GoogleEmailService;
import com.example.kotlinback.common.util.ObjectUtil;
import com.example.kotlinback.member.authcode.AuthCode;
import com.example.kotlinback.member.authcode.AuthCodeRedisRepository;
import com.example.kotlinback.member.dto.LoginDto;
import com.example.kotlinback.member.dto.MemberDto;
import com.example.kotlinback.member.dto.SignUpDto;
import com.example.kotlinback.member.entity.Member;
import com.example.kotlinback.member.refresh.entity.RefreshToken;
import com.example.kotlinback.member.refresh.repository.RefreshTokenRedisRepository;
import com.example.kotlinback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    // 30 minutes
    private final static int ACCESS_TOKEN_MAXAGE = 60 * 30;
    private static final String NOT_CORRECT_TWO_PASSWORD = "두 개의 비밀번호가 일치하지 않습니다.";
    private static final String NICKNAME_CANNOT_USED = "사용할 수 없는 닉네임입니다.";
    private final static String EMAIL_DUPLICATION_MSG = "사용할 수 없는 이메일입니다.";
    private final static String EMAIL_FAIL_AUTH = "인증에 실패했습니다.\n다시 시도해주세요.";
    private final static String EMAIL_NOT_CERTIFIED = "인증되지 않은 이메일입니다.\n인증 후 시도해주세요.";
    private static final String USERNAME_CANNOT_USED = "사용할 수 없는 아이디입니다.";
    private static final String NOT_FOUND_MEMBER = "회원 정보를 찾을 수 없습니다.";
    private static final String NOT_RIGHT_LOGIN_INFO = "아이디 혹은 비밀번호를 확인하세요.";
    private final static String INVALID_REQUEST_MSG = "유효하지 않은 요청입니다.";
    private final static String EXPIRE_RELOGIN_MSG = "기간 만료 : 재로그인해주세요.";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final GoogleEmailService googleEmailService;
    private final AuthCodeRedisRepository authCodeRedisRepository;

    @Override
    public void signUp(SignUpDto signUpDto) {
        if (!signUpDto.getPassword1().equals(signUpDto.getPassword2())) {
            throw new NotCorrectTwoPasswordException(NOT_CORRECT_TWO_PASSWORD);
        }
        signUpDto.setPassword1(passwordEncoder.encode(signUpDto.getPassword1()));
        Optional<AuthCode> optionalAuthCode = authCodeRedisRepository.findById(signUpDto.getEmail());
        if (optionalAuthCode.isEmpty() || !optionalAuthCode.get().getCertifiedYn()) {
            throw new NotCertifiedEmailException(EMAIL_NOT_CERTIFIED);
        }
        Member member = Member.from(MemberDto.from(signUpDto));
        memberRepository.save(member);
        authCodeRedisRepository.delete(optionalAuthCode.get());
    }

    @Override
    public Map<String, String> login(LoginDto loginDto) {
        Member member = ObjectUtil.isNullExceptionElseReturnObJect(memberRepository.findByUsername(loginDto.getUsername()), NOT_FOUND_MEMBER);
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new NotRightLoginInfoException(NOT_RIGHT_LOGIN_INFO);
        }
        return Map.of("accessToken", generateAccessToken(member), "refreshToken", generateRefreshToken(member));
    }

    private String generateAccessToken(Member member) {
        return jwtProvider.generateAccessToken(member.getAccessTokenClaims(), ACCESS_TOKEN_MAXAGE);
    }

    private String generateRefreshToken(Member member) {

        String refreshTokenString = generateRefreshToken(member.getCreateDateTime(), member.getMemId());
        try {
            RefreshToken refreshToken = ObjectUtil.isNullExceptionElseReturnObJect(refreshTokenRedisRepository.findById(member.getMemId()));
            refreshToken.update(refreshTokenString);
            refreshTokenRedisRepository.save(refreshToken);
        } catch (NullPointerException e) {
            return refreshTokenRedisRepository.save(RefreshToken.from(member.getMemId(), refreshTokenString)).getRefreshToken();
        }
        return refreshTokenString;
    }

    private String generateRefreshToken(LocalDateTime createDateTime, String memId) {
        return new StringBuilder()
                .append(createDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                .append(memId)
                .append(UUID.randomUUID())
                .toString()
                ;
    }

    @Override
    public void confirmMemberByNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        if (optionalMember.isPresent()) {
            throw new AlreadyExistMemberException(NICKNAME_CANNOT_USED);
        }
    }

    @Override
    public void confirmMemberByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (optionalMember.isPresent()) {
            throw new AlreadyExistMemberException(USERNAME_CANNOT_USED);
        }
    }

    @Override
    public MemberDto getByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (optionalMember.isEmpty()) {
            throw new NotFoundMemberException(NOT_FOUND_MEMBER);
        }
        return optionalMember.get().toDto();
    }

    @Override
    public String getAccessTokenWithRefreshToken(String inputRefreshToken) {
        RefreshToken refreshToken = ObjectUtil.isNullExceptionElseReturnObJect(refreshTokenRedisRepository.findByRefreshToken(inputRefreshToken), EXPIRE_RELOGIN_MSG);

        return generateAccessToken(ObjectUtil.isNullExceptionElseReturnObJect(memberRepository.findById(refreshToken.getId())));
    }

    @Override
    public void signOut(String memId) {
        deleteRefreshToken(memId);
    }

    @Override
    public void sendEmailAndSaveTempData(String email) {
        // 이메일에 대응하는 key 생성
        // 이메일 전송
        // redis에 email - key 저장
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new DataIntegrityViolationException(EMAIL_DUPLICATION_MSG);
        }
        String authCode = ObjectUtil.generateRandomStringOnlyNumber();
        googleEmailService.sendEmail(MailTo.sendEmailAuthCode(authCode, email));
        authCodeRedisRepository.save(AuthCode.from(authCode, email));
    }

    @Override
    public void confirmEmailAndCode(String email, String code) {
        AuthCode authCode = ObjectUtil.isNullExceptionElseReturnObJect(authCodeRedisRepository.findById(email), EMAIL_FAIL_AUTH);
        if (!authCode.getCode().equals(code)) {
            throw new NotCorrectEmailAuthCodeException(EMAIL_FAIL_AUTH);
        }
        authCode.setCertifiedYn(true);
        authCodeRedisRepository.save(authCode);
    }

    private void deleteRefreshToken(String memId) {
        refreshTokenRedisRepository.deleteById(memId);
    }
}
