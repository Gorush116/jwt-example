package com.colabear754.authentication_example_java.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI swaggerApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("스프링시큐리티 + JWT 예제")
                        .description("스프링시큐리티와 JWT를 이용한 사용자 인증 예제입니다.")
                        .version("1.0.0"));
    }
}
