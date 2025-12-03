package com.newfeed.backend.domain.user.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String email;
    private String nickname;
    private String loginType;
    private String socialId;
    private LocalDateTime createdAt;
}
