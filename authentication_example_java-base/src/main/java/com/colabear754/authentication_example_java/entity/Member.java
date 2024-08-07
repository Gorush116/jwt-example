package com.colabear754.authentication_example_java.entity;

import com.colabear754.authentication_example_java.common.MemberType;
import com.colabear754.authentication_example_java.dto.member.request.MemberUpdateRequest;
import com.colabear754.authentication_example_java.dto.sign_up.request.SignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Member {
    @Column(nullable = false, scale = 20, unique = true)
    private String account;
    @Column(nullable = false)
    private String password;
    private String name;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private MemberType type;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public static Member from(SignUpRequest request, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
        return Member.builder()
                .account(request.account())
                .password(encoder.encode(request.password()))	// 수정
                .name(request.name())
                .age(request.age())
                .type(MemberType.USER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void update(MemberUpdateRequest newMember, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
        this.password = newMember.newPassword() == null || newMember.newPassword().isBlank()
                ? this.password : encoder.encode(newMember.newPassword());	// 수정
        this.name = newMember.name();
        this.age = newMember.age();
    }
}
