package com.newfeed.backend.domain.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {

    private String loginType;
    private String socialId;
    private String email;
    private String nickname;
}
