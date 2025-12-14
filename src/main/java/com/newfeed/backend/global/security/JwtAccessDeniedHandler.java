package com.newfeed.backend.global.security;

import com.newfeed.backend.global.error.CommonErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.access.AccessDeniedException accessDeniedException
    ) throws IOException {

        var error = CommonErrorCode.FORBIDDEN;

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
