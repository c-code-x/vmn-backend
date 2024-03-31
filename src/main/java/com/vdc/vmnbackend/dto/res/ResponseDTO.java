package com.vdc.vmnbackend.dto.res;

/**
 * Represents a response DTO containing data and a basic response.
 *
 * @param <D> The type of data contained in the response.
 */
public record ResponseDTO<D>(
        /**
         * The data contained in the response.
         */
        D data,

        /**
         * The basic response containing message and status.
         */
        BasicResDTO res
) {
}
