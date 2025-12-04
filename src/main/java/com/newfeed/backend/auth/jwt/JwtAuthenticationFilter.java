package com.newfeed.backend.auth.jwt;

import com.newfeed.backend.auth.model.Role;
import com.newfeed.backend.auth.principal.AuthPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    // private final UserDetailsService userDetailsService;  // JWT 기반이라 필요 없음

    /** 매 요청마다 JWT 인증 처리 */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 요청 헤더에서 토큰 꺼내기
        String token = resolveToken(request);

        // 토큰 검증
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // JWT 내부 값 추출
            Long userId = jwtTokenProvider.getUserId(token);
            String email = jwtTokenProvider.getEmail(token);

            // enum Role 사용
            Role role = jwtTokenProvider.getRole(token);   // GUEST, USER, ADMIN

            if (userId < 0) {
                log.debug("GUEST access detected. userId={}", userId);

                AuthPrincipal guestPrincipal =
                        new AuthPrincipal(userId, email, Role.GUEST);

                UsernamePasswordAuthenticationToken guestAuth =
                        new UsernamePasswordAuthenticationToken(
                                guestPrincipal,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_GUEST"))
                        );

                SecurityContextHolder.getContext().setAuthentication(guestAuth);

                filterChain.doFilter(request, response);
                return; // ⚠ 중요: 정식 회원 처리로 넘어가지 않음
            }

            // JWT 기반 인증을 위한 Principal (세션 방식이 아닌 DTO 형태)
            AuthPrincipal principal = new AuthPrincipal(userId, email, role);

            // SecurityContext 에 넣을 Authentication 객체 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
                    );

            // SecurityContextHolder 에 저장 → Controller @AuthenticationPrincipal 로 접근 가능
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 아래는 JWT 기반 인증이므로 사용하지 않는 세션 기반 인증 방식 (참고용으로 주석 유지)
            /*
            // DB에서 사용자 정보 조회 (UserDetails)
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(String.valueOf(userId));

            // SecurityContext에 인증 정보 저장 (세션 기반)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            */
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    /** Authorization 헤더에서 JWT 추출 */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}
