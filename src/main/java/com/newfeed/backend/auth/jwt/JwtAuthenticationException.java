package com.newfeed.backend.auth.jwt;

import com.newfeed.backend.global.error.TokenErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

    private final TokenErrorCode tokenErrorCode;

    public JwtAuthenticationException(TokenErrorCode code) {
        super(code.getDescription());
        this.tokenErrorCode = code;
    }
}
