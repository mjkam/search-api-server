package com.mjkam.search.api.exception;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR", "server error", LogLevel.ERROR),
    INVALID_QUERY_PARAM_ERROR(HttpStatus.BAD_REQUEST, "INVALID_QUERY", "invalid query value", LogLevel.INFO),
    INVALID_PAGE_PARAM_ERROR(HttpStatus.BAD_REQUEST, "INVALID_PAGE", "invalid page value", LogLevel.INFO),
    INVALID_SIZE_PARAM_ERROR(HttpStatus.BAD_REQUEST, "INVALID_SIZE", "invalid size value", LogLevel.INFO),
    INVALID_SORT_PARAM_ERROR(HttpStatus.BAD_REQUEST, "INVALID_SORT", "invalid sort value", LogLevel.INFO),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final LogLevel logLevel;

    ErrorType(HttpStatus status, String code, String message, LogLevel logLevel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }
}
