package com.newfeed.backend.domain.auth.service;

import com.newfeed.backend.auth.jwt.JwtTokenProvider;
import com.newfeed.backend.auth.model.Role;
import com.newfeed.backend.domain.auth.model.GuestLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
