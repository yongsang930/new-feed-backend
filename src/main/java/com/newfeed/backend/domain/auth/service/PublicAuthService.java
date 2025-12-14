package com.newfeed.backend.domain.auth.service;

import com.newfeed.backend.auth.jwt.JwtTokenProvider;
import com.newfeed.backend.auth.model.Role;
import com.newfeed.backend.domain.auth.entity.RefreshToken;
import com.newfeed.backend.domain.auth.model.GuestLoginResponse;
import com.newfeed.backend.domain.auth.model.TokenResponse;
import com.newfeed.backend.global.error.TokenErrorCode;
import com.newfeed.backend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PublicAuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public GuestLoginResponse guestLogin() {

        long guestUserId = -ThreadLocalRandom.current()
                .nextLong(1, Long.MAX_VALUE);

        String accessToken = jwtTokenProvider.createAccessToken(
                guestUserId,
                "guest@local",
                Role.GUEST
        );

        String refreshToken = jwtTokenProvider.createRefreshToken(guestUserId);

        refreshTokenService.save(guestUserId, refreshToken);   // 여기만 DB INSERT

        return new GuestLoginResponse(accessToken, refreshToken, "UNLIMITED", Role.GUEST);
    }

    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return;  // refreshToken이 없으면 아무것도 안 하고 종료
        }

        RefreshToken token = refreshTokenService.findByToken(refreshToken);
        if (token != null) {
            refreshTokenService.deleteByUserId(token.getUserId());
        }
    }

    /**
     * Refresh Token을 이용해 AccessToken 재발급
     */
    public TokenResponse refresh(String refreshToken) {

        // 1) JWT 자체 검증
        jwtTokenProvider.validateRefreshToken(refreshToken);

        // 2) DB 조회
        RefreshToken entity = refreshTokenService.findByToken(refreshToken);
        if (entity == null) {
            throw new ApiException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        // 3) DB 만료 확인
        if (entity.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new ApiException(TokenErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        entity.setLastUsedAt(LocalDateTime.now());
        refreshTokenService.update(entity);

        Long userId = entity.getUserId();
        String email = userId < 0 ? "guest@local" : "user@local";
        Role role = userId < 0 ? Role.GUEST : Role.USER;

        String newAccess = jwtTokenProvider.createAccessToken(userId, email, role);

        return new TokenResponse(newAccess);
    }

}
