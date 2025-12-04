package com.newfeed.backend.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.newfeed.backend.auth.model.Role;

@Data
@AllArgsConstructor
public class GuestLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String expiredAt;
    private Role role;
}
