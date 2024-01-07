package com.vdc.vmnbackend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@Setter
public class ApiRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ApiRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
