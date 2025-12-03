package com.newfeed.backend.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponse {
    private Long userId;
    private String email;
    private String role;
}
