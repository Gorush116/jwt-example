package com.colabear754.authentication_example_java;

import com.colabear754.authentication_example_java.common.MemberType;
import com.colabear754.authentication_example_java.entity.Member;
import com.colabear754.authentication_example_java.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AdminInitializer implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;	// 추가

    @Override
    public void run(ApplicationArguments args) {
        memberRepository.save(Member.builder()
                .account("admin")
                .password(encoder.encode("admin"))	// 수정
                .name("관리자")
                .type(MemberType.ADMIN)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
