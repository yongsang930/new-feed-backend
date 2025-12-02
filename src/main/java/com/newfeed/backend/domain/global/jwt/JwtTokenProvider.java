package com.newfeed.backend.domain.global.jwt;

import io.jsonwebtoken.Claims;
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

    /**
     * HMAC 서명 Key 생성
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    /**
     * AccessToken 생성
     */
    public String createAccessToken(Long userId, String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(userId))  // 0.12.x: setSubject → subject()
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())        // 0.12.x: 두 번째 인자(SignatureAlgorithm) 필요 없음
                .compact();
    }

    /**
     * RefreshToken 생성
     */
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

    /**
     * 토큰에서 사용자 PK(userId) 추출
     */
    public Long getUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token); // 파싱 성공하면 유효
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 서명 불일치, 만료, 변조 등
        }
    }

    /**
     * Claims 파싱 (0.12.x 전용 문법)
     */
    private Claims getClaims(String token) {
        return Jwts.parser()                     // parserBuilder() → parser()
                .verifyWith(getSigningKey())     // verifyWith(key)
                .build()
                .parseSignedClaims(token)        // parseClaimsJws() → parseSignedClaims()
                .getPayload();                   // getBody() → getPayload()
    }

    /** 🔥 Refresh Token 만료시간 LocalDateTime으로 변환 */
    public LocalDateTime getRefreshTokenExpiry() {
        return LocalDateTime.now().plus(
                java.time.Duration.ofMillis(jwtProperties.getRefreshTokenExpiration())
        );
    }
}
