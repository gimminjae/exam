package com.example.kotlinback.member.service

import com.example.kotlinback.k.common.jwt.provider.JwtProvider
import com.example.kotlinback.k.common.mail.dto.MailTo
import com.example.kotlinback.k.common.mail.service.GoogleEmailService
import com.example.kotlinback.k.common.util.ObjectUtil
import com.example.kotlinback.k.member.authcode.AuthCode
import com.example.kotlinback.k.member.authcode.AuthCodeRedisRepository
import com.example.kotlinback.k.member.dto.LoginDto
import com.example.kotlinback.k.member.dto.MemberDto
import com.example.kotlinback.k.member.dto.SignUpDto
import com.example.kotlinback.k.member.entity.Member
import com.example.kotlinback.k.member.refresh.entity.RefreshToken
import com.example.kotlinback.k.member.refresh.repository.RefreshTokenRedisRepository
import com.example.kotlinback.k.member.repository.MemberRepository
import lombok.RequiredArgsConstructor
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
@RequiredArgsConstructor
class MemberServiceImpl : MemberService {
    private val memberRepository: MemberRepository? = null
    private val passwordEncoder: PasswordEncoder? = null
    private val jwtProvider: JwtProvider? = null
    private val refreshTokenRedisRepository: RefreshTokenRedisRepository? = null
    private val googleEmailService: GoogleEmailService? = null
    private val authCodeRedisRepository: AuthCodeRedisRepository? = null
    override fun signUp(signUpDto: SignUpDto?) {
        if (signUpDto.getPassword1() != signUpDto.getPassword2()) {
            throw NotCorrectTwoPasswordException(NOT_CORRECT_TWO_PASSWORD)
        }
        signUpDto.setPassword1(passwordEncoder!!.encode(signUpDto.getPassword1()))
        val optionalAuthCode = authCodeRedisRepository!!.findById(signUpDto.getEmail())
        if (optionalAuthCode.isEmpty || !optionalAuthCode.get().certifiedYn) {
            throw NotCertifiedEmailException(EMAIL_NOT_CERTIFIED)
        }
        val member: Member = Member.Companion.from(MemberDto.Companion.from(signUpDto))
        memberRepository!!.save(member)
        authCodeRedisRepository.delete(optionalAuthCode.get())
    }

    override fun login(loginDto: LoginDto?): Map<String, String?>? {
        val member = ObjectUtil.isNullExceptionElseReturnObJect(
            memberRepository!!.findByUsername(loginDto.getUsername()), NOT_FOUND_MEMBER
        )
        if (!passwordEncoder!!.matches(loginDto.getPassword(), member.password)) {
            throw NotRightLoginInfoException(NOT_RIGHT_LOGIN_INFO)
        }
        return java.util.Map.of(
            "accessToken",
            generateAccessToken(member),
            "refreshToken",
            generateRefreshToken(member)
        )
    }

    private fun generateAccessToken(member: Member?): String? {
        return jwtProvider!!.generateAccessToken(member.getAccessTokenClaims(), ACCESS_TOKEN_MAXAGE)
    }

    private fun generateRefreshToken(member: Member?): String {
        val refreshTokenString = generateRefreshToken(member.getCreateDateTime(), member.getMemId())
        try {
            val refreshToken = ObjectUtil.isNullExceptionElseReturnObJect(
                refreshTokenRedisRepository!!.findById(member.getMemId())
            )
            refreshToken!!.update(refreshTokenString)
            refreshTokenRedisRepository.save(refreshToken)
        } catch (e: NullPointerException) {
            return refreshTokenRedisRepository!!.save<RefreshToken>(
                RefreshToken.Companion.from(
                    member.getMemId(),
                    refreshTokenString
                )
            ).refreshToken
        }
        return refreshTokenString
    }

    private fun generateRefreshToken(createDateTime: LocalDateTime, memId: String): String {
        return StringBuilder()
            .append(createDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
            .append(memId)
            .append(UUID.randomUUID())
            .toString()
    }

    override fun confirmMemberByNickname(nickname: String?) {
        val optionalMember = memberRepository!!.findByNickname(nickname)
        if (optionalMember!!.isPresent) {
            throw AlreadyExistMemberException(NICKNAME_CANNOT_USED)
        }
    }

    override fun confirmMemberByUsername(username: String?) {
        val optionalMember = memberRepository!!.findByUsername(username)
        if (optionalMember!!.isPresent) {
            throw AlreadyExistMemberException(USERNAME_CANNOT_USED)
        }
    }

    override fun getByUsername(username: String?): MemberDto? {
        val optionalMember = memberRepository!!.findByUsername(username)
        if (optionalMember!!.isEmpty) {
            throw NotFoundMemberException(NOT_FOUND_MEMBER)
        }
        return optionalMember.get().toDto()
    }

    override fun getAccessTokenWithRefreshToken(inputRefreshToken: String?): String? {
        val refreshToken = ObjectUtil.isNullExceptionElseReturnObJect(
            refreshTokenRedisRepository!!.findByRefreshToken(inputRefreshToken), EXPIRE_RELOGIN_MSG
        )
        return generateAccessToken(
            ObjectUtil.isNullExceptionElseReturnObJect(
                memberRepository!!.findById(refreshToken.id)
            )
        )
    }

    override fun signOut(memId: String) {
        deleteRefreshToken(memId)
    }

    override fun sendEmailAndSaveTempData(email: String?) {
        // 이메일에 대응하는 key 생성
        // 이메일 전송
        // redis에 email - key 저장
        if (memberRepository!!.findByEmail(email).isPresent) {
            throw DataIntegrityViolationException(EMAIL_DUPLICATION_MSG)
        }
        val authCode = ObjectUtil.generateRandomStringOnlyNumber()
        googleEmailService!!.sendEmail(MailTo.Companion.sendEmailAuthCode(authCode, email))
        authCodeRedisRepository!!.save<AuthCode>(AuthCode.Companion.from(authCode, email))
    }

    override fun confirmEmailAndCode(email: String, code: String) {
        val authCode = ObjectUtil.isNullExceptionElseReturnObJect(
            authCodeRedisRepository!!.findById(email), EMAIL_FAIL_AUTH
        )
        if (authCode.code != code) {
            throw NotCorrectEmailAuthCodeException(EMAIL_FAIL_AUTH)
        }
        authCode!!.certifiedYn = true
        authCodeRedisRepository.save(authCode)
    }

    private fun deleteRefreshToken(memId: String) {
        refreshTokenRedisRepository!!.deleteById(memId)
    }

    companion object {
        // 30 minutes
        private const val ACCESS_TOKEN_MAXAGE = 60 * 30
        private const val NOT_CORRECT_TWO_PASSWORD = "두 개의 비밀번호가 일치하지 않습니다."
        private const val NICKNAME_CANNOT_USED = "사용할 수 없는 닉네임입니다."
        private const val EMAIL_DUPLICATION_MSG = "사용할 수 없는 이메일입니다."
        private const val EMAIL_FAIL_AUTH = "인증에 실패했습니다.\n다시 시도해주세요."
        private const val EMAIL_NOT_CERTIFIED = "인증되지 않은 이메일입니다.\n인증 후 시도해주세요."
        private const val USERNAME_CANNOT_USED = "사용할 수 없는 아이디입니다."
        private const val NOT_FOUND_MEMBER = "회원 정보를 찾을 수 없습니다."
        private const val NOT_RIGHT_LOGIN_INFO = "아이디 혹은 비밀번호를 확인하세요."
        private const val INVALID_REQUEST_MSG = "유효하지 않은 요청입니다."
        private const val EXPIRE_RELOGIN_MSG = "기간 만료 : 재로그인해주세요."
    }
}
