package com.newfeed.backend.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/open-api/**" // 덤으로 넣음
            
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Spring Security의 보안 필터 설정 시작.
        httpSecurity
                // CSRF 보호 기능을 명시적으로 끈다. (REST API에서 대부분 비활성화함)
                .csrf(csrf -> csrf.disable())
                // HTTP 요청들에 대한 접근 권한 설정 시작
                .authorizeHttpRequests(it -> {
                    // CSS/JS/이미지 같은 정적 리소스 경로는 인증 없이 접근 허용.
                    it
                            .requestMatchers(
                                    PathRequest.toStaticResources().atCommonLocations()
                            ).permitAll() // resource에 대해서는 모든 요청 허용

                    // Swagger 관련 경로(swagger-ui, v3/api-docs 등)는 인증 없이 접근 허용.
                    .requestMatchers(
                            SWAGGER.toArray(new String[0])
                    ).permitAll()

                    // 위에서 허가하지 않은 모든 요청은 로그인(인증) 필요.
                    .anyRequest().authenticated();
                })
                // 기본 form 로그인 방식 사용 (스프링 제공 기본 로그인 화면)
                .formLogin(form -> form
                        .defaultSuccessUrl("/main", true)
                );

        //설정된 SecurityFilterChain 생성 및 반환
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        
        // hash로 암호화
        return new BCryptPasswordEncoder();
    }

}
