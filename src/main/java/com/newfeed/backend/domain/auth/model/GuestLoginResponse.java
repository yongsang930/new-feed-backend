package com.newfeed.backend.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String expiredAt;
}
