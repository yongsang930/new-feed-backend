package com.newfeed.backend.domain.auth.service;

import com.newfeed.backend.auth.jwt.JwtTokenProvider;
import com.newfeed.backend.auth.model.Role;
import com.newfeed.backend.domain.auth.model.GuestLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicAuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public GuestLoginResponse guestLogin() {

        Long guestUserId = 0L;  // 게스트 유저 고정 아이디 or 랜덤 uuid

        String accessToken = jwtTokenProvider.createAccessToken(guestUserId, "guest@guest.local", Role.USER);
        String refreshToken = jwtTokenProvider.createRefreshToken(guestUserId);

        return new GuestLoginResponse(accessToken, refreshToken, "UNLIMITED");
    }
}
