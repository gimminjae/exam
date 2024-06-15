package com.example.kotlinback.member.entity

import com.example.kotlinback.common.generator.IdGenerator
import com.example.kotlinback.k.member.dto.MemberDto
import com.example.kotlinback.k.member.dto.MemberType
import com.example.kotlinback.k.member.dto.Role
import jakarta.persistence.*
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.time.LocalDateTime

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
@DynamicInsert // 엔티티를 save할 때 null 값은 배제하고 insert 쿼리를 날리도록 한다.

class Member @Builder private constructor(
    @field:Column(
        name = "createDateTime",
        nullable = false
    ) private val createDateTime: LocalDateTime,
    @field:Column(
        name = "updateDateTime",
        nullable = false
    ) private val updateDateTime: LocalDateTime,
    @field:Column(
        columnDefinition = "varchar(50)",
        name = "username",
        nullable = false,
        unique = true
    ) private val username: String,
    @field:Column(
        columnDefinition = "varchar(50)",
        name = "email",
        nullable = false,
        unique = true
    ) private val email: String,
    @field:Column(
        columnDefinition = "varchar(255)",
        name = "password"
    ) private val password: String,
    @field:Column(
        columnDefinition = "varchar(50)",
        name = "nickname",
        nullable = false,
        unique = true
    ) private val nickname: String,
    @field:Enumerated(value = EnumType.STRING) @field:Column(
        columnDefinition = "varchar(10)",
        name = "memType",
        nullable = false
    ) private val memType: MemberType
) {
    @Id
    @Column(columnDefinition = "varchar(100)", name = "memId")
    @GenericGenerator(
        name = "memId",
        strategy = "com.example.kotlinback.common.generator.IdGenerator",
        parameters = [Parameter(name = IdGenerator.ENTITY_TYPE, value = "M")]
    )
    @GeneratedValue(generator = "memId")
    private val memId: String? = null

    @Column(columnDefinition = "boolean", name = "useYn", nullable = false)
    @ColumnDefault("true")
    private val useYn: Boolean? = null

    @Column(columnDefinition = "varchar(10)", name = "role", nullable = false)
    @ColumnDefault("'MEMBER'")
    @Enumerated(value = EnumType.STRING)
    private val role: Role? = null
    fun toDto(): MemberDto {
        return MemberDto.builder()
            .memId(this.getMemId())
            .createDateTime(LocalDateTime.now())
            .updateDateTime(LocalDateTime.now())
            .username(this.getUsername())
            .nickname(this.getNickname())
            .password(this.getPassword())
            .memType(this.getMemType().getTypeName())
            .role(this.getRole().getRole())
            .useYn(this.getUseYn())
            .build()
    }

    val accessTokenClaims: Map<String, Any>
        get() = java.util.Map.of<String, Any>(
            "username", getUsername(),
            "createDateTime", getCreateDateTime().toString(),
            "nickname", getNickname(),
            "role", getRole().getRole()
        )

    companion object {
        fun from(memberDto: MemberDto): Member {
            return Member.builder()
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .username(memberDto.username)
                .email(memberDto.email)
                .nickname(memberDto.nickname)
                .password(memberDto.password)
                .memType(MemberType.valueOf(memberDto.memType))
                .build()
        }
    }
}
