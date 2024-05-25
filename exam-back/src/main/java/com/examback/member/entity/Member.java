package com.examback.member.entity;

import com.examback.member.dto.MemberDto;
import com.examback.member.dto.MemberType;
import com.examback.member.dto.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDateTime;
import java.util.Map;

import static com.examback.common.generator.IdGenerator.ENTITY_TYPE;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
@DynamicInsert // 엔티티를 save할 때 null 값은 배제하고 insert 쿼리를 날리도록 한다.
public class Member {
    @Id
    @Column(columnDefinition = "varchar(100)", name = "memId")
    @GenericGenerator(
            name = "memId",
            strategy = "com.examback.common.generator.IdGenerator",
            parameters = {
                    @Parameter(name = ENTITY_TYPE, value = "M"),
            }
    )
    @GeneratedValue(generator = "memId")
    private String memId;

    @Column(name = "createDateTime", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "updateDateTime", nullable = false)
    private LocalDateTime updateDateTime;

    @Column(columnDefinition = "varchar(50)", name = "username", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "varchar(255)", name = "password")
    private String password;

    @Column(columnDefinition = "varchar(50)", name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(columnDefinition = "varchar(10)", name = "memType", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberType memType;

    @Column(columnDefinition = "boolean", name = "useYn", nullable = false)
    @ColumnDefault("true")
    private Boolean useYn;

    @Column(columnDefinition = "varchar(10)", name = "role", nullable = false)
    @ColumnDefault("'MEMBER'")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    private Member(LocalDateTime createDateTime,
                   LocalDateTime updateDateTime,
                   String username,
                   String password,
                   String nickname,
                   MemberType memType) {
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.memType = memType;
    }

    public static Member from(MemberDto memberDto) {
        return Member.builder()
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .username(memberDto.getUsername())
                .nickname(memberDto.getNickname())
                .password(memberDto.getPassword())
                .memType(MemberType.valueOf(memberDto.getMemType()))
                .build();
    }

    public MemberDto toDto() {
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
                .build();
    }
    public Map<String, Object> getAccessTokenClaims() {
        return Map.of(
                "username", getUsername(),
                "createDateTime", getCreateDateTime().toString(),
                "nickname", getNickname(),
                "role", getRole().getRole()
        );
    }
}
