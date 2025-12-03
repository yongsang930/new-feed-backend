package com.newfeed.backend.domain.auth.controller;

import com.newfeed.backend.domain.auth.model.GuestLoginResponse;
import com.newfeed.backend.domain.auth.service.PublicAuthService;
import com.newfeed.backend.global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PublicAuthController {

    private final PublicAuthService publicAuthService;

    @PostMapping("/guest")
    public Api<GuestLoginResponse> guestLogin() {
        return Api.OK(publicAuthService.guestLogin());
    }
}
