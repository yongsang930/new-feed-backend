package com.newfeed.backend.auth.principal;

import com.newfeed.backend.auth.model.Role;

public record AuthPrincipal(
        Long userId,
        String email,
        Role role
) {}
