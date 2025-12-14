package com.newfeed.backend.global.security;

import com.newfeed.backend.global.error.TokenErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException
    ) throws IOException {

        TokenErrorCode error = TokenErrorCode.ACCESS_TOKEN_EXPIRED;

        response.setStatus(error.getHttpStatusCode());
        response.setContentType("application/json; charset=UTF-8");

        var body = String.format(
                "{\"result\": {\"httpStatusCode\": %d, \"errorCode\": %d, \"description\": \"%s\"}}",
                error.getHttpStatusCode(),
                error.getErrorCode(),
                error.getDescription()
        );

        response.getWriter().write(body);
    }
}
