package com.newfeed.backend.domain.user.controller;

import com.newfeed.backend.domain.user.model.UserMeResponse;
import com.newfeed.backend.global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public Api<?> me() {
        // 게스트 유저 정보 반환
        return Api.OK(
                new UserMeResponse(
                        0L,
                        "guest@guest.local",
                        "GUEST"
                )
        );
    }
}
