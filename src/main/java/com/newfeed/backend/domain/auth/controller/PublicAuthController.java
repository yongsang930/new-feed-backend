package com.newfeed.backend.domain.auth.controller;

import com.newfeed.backend.domain.auth.model.GuestLoginResponse;
import com.newfeed.backend.domain.auth.model.TokenResponse;
import com.newfeed.backend.domain.auth.service.PublicAuthService;
import com.newfeed.backend.global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PublicAuthController {

    private final PublicAuthService publicAuthService;

    @PostMapping("/refresh")
    public Api<TokenResponse> refresh(@RequestHeader("refresh-token") String refreshToken) {
        return Api.OK(publicAuthService.refresh(refreshToken));
    }

    @PostMapping("/guest")
    public Api<GuestLoginResponse> guestLogin() {
        return Api.OK(publicAuthService.guestLogin());
    }

    @PostMapping("/logout")
    public Api<?> logout(@RequestHeader("Authorization") String authorization,
                         @RequestHeader(value = "Refresh-Token", required = false) String refreshToken) {

        // Authorization: Bearer xxx
        String accessToken = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            accessToken = authorization.substring(7);
        }

        // refresh token 기반 row 삭제
        publicAuthService.logout(refreshToken);

        return Api.OK("로그아웃 되었습니다.");
    }

}
