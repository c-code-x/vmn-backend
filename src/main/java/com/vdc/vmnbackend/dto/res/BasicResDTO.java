package com.vdc.vmnbackend.dto.res;

import org.springframework.http.HttpStatus;

/**
 * Represents a basic response DTO containing a message and HTTP status.
 */
public record BasicResDTO(
        /**
         * The message associated with the response.
         */
        String message,

        /**
         * The HTTP status of the response.
         */
        HttpStatus status
) {
}
