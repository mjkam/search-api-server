package com.mjkam.search.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_ERROR"),
    ;

    private final HttpStatus httpStatus;
    private final String code;

    ExceptionType(HttpStatus httpStatus, String code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
