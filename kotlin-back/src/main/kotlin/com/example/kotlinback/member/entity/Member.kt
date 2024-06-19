package com.example.kotlinback.member.entity

import com.example.kotlinback.global.BooleanToYNConverter
import com.example.kotlinback.global.generator.IdGenerator
import com.example.kotlinback.member.dto.MemberDto
import com.example.kotlinback.member.dto.MemberType
import com.example.kotlinback.member.dto.Role
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime

@Entity
@Table(name = "MEMBER")
class Member(
    @Id
    @Column(name = "MEM_ID", length = 50)
    @GenericGenerator(
        name = "memId",
        type = IdGenerator::class
    )
    @GeneratedValue(generator = "memId")
    val memId: String = "",

    @Column(name = "ROLE", nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    var role: Role = Role.MEMBER,

    @Column(name = "CREATE_DT", nullable = false)
    var createDateTime: LocalDateTime,

    @Column(name = "UPDATE_DT", nullable = false)
    var updateDateTime: LocalDateTime,

    @Column(name = "USERNAME", nullable = false, unique = true, length = 50)
    var username: String = "",

    @Column(name = "EMAIL", nullable = false, unique = true, length = 50)
    var email: String = "",

    @Column(name = "PASSWORD", length = 50)
    var password: String = "",

    @Column(name = "NICKNAME", nullable = false, unique = true, length = 50)
    var nickname: String = "",

    @Enumerated(value = EnumType.STRING)
    @Column(name = "MEM_TYPE", nullable = false, length = 50)
    var memType: MemberType = MemberType.COMMON,

    @Column(name = "DEL_YN", nullable = false)
    @Convert(converter = BooleanToYNConverter::class)
    var delYn: Boolean = false
) {
    fun toDto(): MemberDto {
        return MemberDto(
            memId = this.memId,
            createDateTime = this.createDateTime,
            updateDateTime = this.updateDateTime,
            username = this.username,
            nickname = this.nickname,
            password = this.password,
            memType = this.memType.name,
            role = this.role.name,
            useYn = this.delYn,
            email = this.email
        )
    }

    val accessTokenClaims: Map<String, Any>
        get() = mapOf<String, Any>(
            "username" to username,
            "createDateTime" to createDateTime.toString(),
            "nickname" to nickname,
            "role" to role.name
        )

    companion object {
        fun from(memberDto: MemberDto): Member {
            return Member(
                createDateTime = LocalDateTime.now(),
                updateDateTime = LocalDateTime.now(),
                username = memberDto.username,
                email = memberDto.email,
                nickname = memberDto.nickname,
                password = memberDto.password,
                memType = MemberType.valueOf(memberDto.memType)
            )
        }
    }
}
