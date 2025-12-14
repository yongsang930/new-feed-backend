package com.newfeed.backend.global.exceptionhandler;

import com.newfeed.backend.global.api.Api;
import com.newfeed.backend.global.error.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Api<Object>> handleException(Exception ex) {

        log.error("", ex);

        var error = CommonErrorCode.SERVER_ERROR;

        return ResponseEntity
                .status(error.getHttpStatusCode())
                .body(Api.ERROR(error, error.getDescription()));
    }
}
