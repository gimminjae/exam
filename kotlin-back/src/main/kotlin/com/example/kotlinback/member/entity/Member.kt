package com.example.kotlinback.member.entity

import com.example.kotlinback.common.generator.IdGenerator
import com.example.kotlinback.member.dto.MemberDto
import com.example.kotlinback.member.dto.MemberType
import com.example.kotlinback.member.dto.Role
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.time.LocalDateTime

@Entity
@Table(name = "member")
@DynamicInsert // 엔티티를 save할 때 null 값은 배제하고 insert 쿼리를 날리도록 한다.

class Member(
    @Id
    @Column(columnDefinition = "varchar(100)", name = "memId")
    @GenericGenerator(
        name = "memId",
        strategy = "com.example.kotlinback.common.generator.IdGenerator",
        parameters = [Parameter(name = IdGenerator.ENTITY_TYPE, value = "M")]
    )
    @GeneratedValue(generator = "memId")
    val memId: String = "",

    @Column(columnDefinition = "boolean", name = "useYn", nullable = false)
    @ColumnDefault("true")
    val useYn: Boolean = true,

    @Column(columnDefinition = "varchar(10)", name = "role", nullable = false)
    @ColumnDefault("'MEMBER'")
    @Enumerated(value = EnumType.STRING)
    val role: Role = Role.MEMBER,

    @Column(
        name = "createDateTime",
        nullable = false
    )
    val createDateTime: LocalDateTime,

    @Column(
        name = "updateDateTime",
        nullable = false
    )
    val updateDateTime: LocalDateTime,

    @Column(
        columnDefinition = "varchar(50)",
        name = "username",
        nullable = false,
        unique = true
    )
    val username: String,

    @Column(
        columnDefinition = "varchar(50)",
        name = "email",
        nullable = false,
        unique = true
    )
    val email: String,

    @Column(
        columnDefinition = "varchar(255)",
        name = "password"
    )
    val password: String,

    @Column(
        columnDefinition = "varchar(50)",
        name = "nickname",
        nullable = false,
        unique = true
    )
    val nickname: String,

    @Enumerated(value = EnumType.STRING)
    @Column(
        columnDefinition = "varchar(10)",
        name = "memType",
        nullable = false
    )
    val memType: MemberType
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
            useYn = this.useYn
        )
    }

    val accessTokenClaims: Map<String, Any>
        get() = java.util.Map.of<String, Any>(
            "username", username,
            "createDateTime", createDateTime.toString(),
            "nickname", nickname,
            "role", role?.name
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
