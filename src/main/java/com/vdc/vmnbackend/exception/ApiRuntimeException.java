package com.vdc.vmnbackend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Custom runtime exception for API errors.
 */
@Getter
@Setter
public class ApiRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;

    /**
     * Constructs a new ApiRuntimeException with the specified error message and HTTP status.
     *
     * @param message    The error message.
     * @param httpStatus The HTTP status associated with the error.
     */
    public ApiRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
