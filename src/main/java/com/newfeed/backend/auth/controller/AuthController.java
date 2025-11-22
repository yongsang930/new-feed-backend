package com.newfeed.backend.auth.controller;

import com.newfeed.backend.auth.details.CustomUserDetails;
import com.newfeed.backend.auth.dto.LoginRequest;
import com.newfeed.backend.auth.dto.RefreshRequest;
import com.newfeed.backend.auth.dto.TokenResponse;
import com.newfeed.backend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // 액세스 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails user) {
        authService.logout(user.getUserId());
        return ResponseEntity.ok().build();
    }
}
