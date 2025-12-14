package com.newfeed.backend.auth.jwt;

import com.newfeed.backend.auth.model.Role;
import com.newfeed.backend.global.error.TokenErrorCode;
import com.newfeed.backend.global.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    /** 서명 키 생성 */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    /** Access Token 생성 (USER/ADMIN 권한 포함) */
    public String createAccessToken(Long userId, String email, Role role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    /** Refresh Token 생성 (role 정보 X) */
    public String createRefreshToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    /** 토큰에서 userId 추출 */
    public Long getUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    /** 토큰에서 email 추출 */
    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    /** 토큰에서 Role(enum) 추출 */
    public Role getRole(String token) {
        String role = getClaims(token).get("role", String.class);
        return Role.valueOf(role); // 문자열 → enum 변환
    }

    /**
     * Access Token 검증 → boolean
     */
    public void validateToken(String token) {
        try {
            getClaims(token);

        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(TokenErrorCode.ACCESS_TOKEN_EXPIRED);

        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException(TokenErrorCode.INVALID_TOKEN);
        }
    }

    /** Refresh Token 검증 → ApiException 발생 */
    public void validateRefreshToken(String token) {
        try {
            getClaims(token);
        } catch (ExpiredJwtException e) {
            throw new ApiException(TokenErrorCode.REFRESH_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ApiException(TokenErrorCode.INVALID_TOKEN);
        }
    }

    /** Claims 파싱 */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** RefreshToken 만료 시간 반환 */
    public LocalDateTime getRefreshTokenExpiry() {
        long ms = jwtProperties.getRefreshTokenExpiration();
        return LocalDateTime.now().plus(java.time.Duration.ofMillis(ms));
    }
}
