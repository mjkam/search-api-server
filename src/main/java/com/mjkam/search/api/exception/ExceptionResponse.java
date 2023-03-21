package com.mjkam.search.api.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private final String code;

    public ExceptionResponse(String code) {
        this.code = code;
    }

    public static ExceptionResponse of(ExceptionType exceptionType) {
        return new ExceptionResponse(exceptionType.getCode());
    }
}
