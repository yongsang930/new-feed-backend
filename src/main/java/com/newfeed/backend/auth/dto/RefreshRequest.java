package com.newfeed.backend.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {
    private String refreshToken;
}
