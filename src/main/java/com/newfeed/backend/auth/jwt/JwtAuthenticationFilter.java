package com.newfeed.backend.auth.jwt;

import com.newfeed.backend.auth.model.Role;
import com.newfeed.backend.auth.principal.AuthPrincipal;
import com.newfeed.backend.global.error.TokenErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null) {
            try {

                jwtTokenProvider.validateToken(token);

                Long userId = jwtTokenProvider.getUserId(token);
                String email = jwtTokenProvider.getEmail(token);
                Role role = jwtTokenProvider.getRole(token);

                AuthPrincipal principal = new AuthPrincipal(userId, email, role);

                var auth = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (AuthenticationException ex) {
                SecurityContextHolder.clearContext();
                throw ex; // ExceptionTranslationFilter 로 전달됨
            }
        }

        chain.doFilter(request, response);
    }

    private void writeJwtError(HttpServletResponse response, TokenErrorCode code) throws IOException {
        response.setStatus(code.getHttpStatusCode());
        response.setContentType("application/json; charset=UTF-8");

        String body = String.format(
                "{\"result\": {\"httpStatusCode\": %d, \"errorCode\": %d, \"description\": \"%s\"}}",
                code.getHttpStatusCode(),
                code.getErrorCode(),
                code.getDescription()
        );

        response.getWriter().write(body);
    }

    /** Authorization 헤더에서 JWT 추출 */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/");
    }
}
