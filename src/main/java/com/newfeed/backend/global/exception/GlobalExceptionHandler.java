package com.newfeed.backend.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        ExceptionCode code = e.getExceptionCode();

        ErrorResponse response = new ErrorResponse(
                code.name(),
                code.getMessage(),
                code.getStatus()
        );

        return ResponseEntity
                .status(code.getStatus())
                .body(response);
    }

    // 가장 마지막: 예상치 못한 서버 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ExceptionCode code = ExceptionCode.INTERNAL_SERVER_ERROR;

        ErrorResponse response = new ErrorResponse(
                code.name(),
                code.getMessage(),
                code.getStatus()
        );

        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
