package com.example.bookstore_api.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(
            BusinessException e, HttpServletRequest req) {

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "path", req.getRequestURI(),
                        "status", e.getErrorCode().getStatus().value(),
                        "code", e.getErrorCode().getCode(),
                        "message", e.getMessage()
                ));
    }
}
