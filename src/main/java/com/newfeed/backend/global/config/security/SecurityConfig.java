package com.newfeed.backend.global.config.security;

import com.newfeed.backend.auth.jwt.JwtAuthenticationFilter;
import com.newfeed.backend.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] PUBLIC_URIS = {
            "/auth/**",
            "/oauth2/**",
            "/public/**",
    };

    private static final String[] API = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/auth/**"
    };

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.addAllowedOriginPattern("*");  // 모든 Origin 허용
                    config.addAllowedMethod("*");         // GET, POST 등 전부 허용
                    config.addAllowedHeader("*");         // 헤더 전부 허용
                    config.setAllowCredentials(true);     // Credential 허용
                    return config;
                }))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth ->
                        auth
                                // 인증 불필요
                                .requestMatchers(PUBLIC_URIS).permitAll()
                                .requestMatchers(API).permitAll()

                                // 관리자 전용 API
                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                // 나머지는 일반 유저
                                .anyRequest().hasRole("USER")
                                // JWT 필터 등록
                )
                                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
