package com.newfeed.backend.auth.principal;

import com.newfeed.backend.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthPrincipal {

    private Long userId;
    private String email;
    private Role role;

}
