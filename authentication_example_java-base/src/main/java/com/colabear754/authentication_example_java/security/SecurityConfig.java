package com.colabear754.authentication_example_java.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableMethodSecurity    // 추가
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;	// JwtAuthenticationFilter 주입

    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/sign-up", "/sign-in"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
//                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))	// H2 콘솔 사용을 위한 설정
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(allowedUrls).permitAll()	// 허용할 url 목록을 배열로 분리했다
                                .requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 콘솔 접속은 모두에게 허용
                                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )	// 세션을 사용하지 않으므로 STATELESS 설정
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)	// 추가
                .build();
    }
}