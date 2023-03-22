package com.mjkam.search.api.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiControllerAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR: log.error("ApiException : {}", e.getMessage(), e); break;
            case WARN: log.warn("ApiException : {}", e.getMessage(), e); break;
            default: log.info("ApiException : {}", e.getMessage(), e);
        }
        return ResponseEntity
                .status(e.getErrorType().getStatus())
                .body(new ErrorResponse(e.getErrorType()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Error : {}", e.getMessage(), e);
        return ResponseEntity
                .status(ErrorType.DEFAULT_ERROR.getStatus())
                .body(new ErrorResponse(ErrorType.DEFAULT_ERROR));
    }
}