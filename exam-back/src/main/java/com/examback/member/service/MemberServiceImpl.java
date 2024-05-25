package com.examback.member.service;

import com.examback.common.jwt.provider.JwtProvider;
import com.examback.common.util.ObjectUtil;
import com.examback.member.dto.LoginDto;
import com.examback.member.dto.MemberDto;
import com.examback.member.dto.SignUpDto;
import com.examback.member.entity.Member;
import com.examback.member.exception.AlreadyExistMemberException;
import com.examback.member.exception.NotCorrectTwoPasswordException;
import com.examback.member.exception.NotFoundMemberException;
import com.examback.member.exception.NotRightLoginInfoException;
import com.examback.member.refresh.entity.RefreshToken;
import com.examback.member.refresh.repository.RefreshTokenRedisRepository;
import com.examback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    // 30 minutes
    private final static int ACCESS_TOKEN_MAXAGE = 60 * 30;
    private static final String NOT_CORRECT_TWO_PASSWORD = "두 개의 비밀번호가 일치하지 않습니다.";
    private static final String NICKNAME_CANNOT_USED = "사용할 수 없는 닉네임입니다.";
    private static final String USERNAME_CANNOT_USED = "사용할 수 없는 아이디입니다.";
    private static final String NOT_FOUND_MEMBER = "회원 정보를 찾을 수 없습니다.";
    private static final String NOT_RIGHT_LOGIN_INFO = "아이디 혹은 비밀번호를 확인하세요.";
    private final static String INVALID_REQUEST_MSG = "유효하지 않은 요청입니다.";
    private final static String EXPIRE_RELOGIN_MSG = "기간 만료 : 재로그인해주세요.";

    @Override
    public void signUp(SignUpDto signUpDto) {
        if(!signUpDto.getPassword1().equals(signUpDto.getPassword2())) {
            throw new NotCorrectTwoPasswordException(NOT_CORRECT_TWO_PASSWORD);
        }
        signUpDto.setPassword1(passwordEncoder.encode(signUpDto.getPassword1()));
        Member member = Member.from(MemberDto.from(signUpDto));
        memberRepository.save(member);
    }

    @Override
    public Map<String, String> login(LoginDto loginDto) {
        Member member = ObjectUtil.isNullExceptionElseReturnObJect(memberRepository.findByUsername(loginDto.getUsername()), NOT_FOUND_MEMBER);
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
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
        if(optionalMember.isPresent()) {
            throw new AlreadyExistMemberException(NICKNAME_CANNOT_USED);
        }
    }

    @Override
    public void confirmMemberByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if(optionalMember.isPresent()) {
            throw new AlreadyExistMemberException(USERNAME_CANNOT_USED);
        }
    }

    @Override
    public MemberDto getByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if(optionalMember.isEmpty()) {
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

    private void deleteRefreshToken(String memId) {
        refreshTokenRedisRepository.deleteById(memId);
    }
}
