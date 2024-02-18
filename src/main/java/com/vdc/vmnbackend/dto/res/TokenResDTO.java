package com.vdc.vmnbackend.dto.res;

/**
 * Represents a DTO for holding access and refresh tokens.
 */
public record TokenResDTO(
        /**
         * The access token.
         */
        String accessToken,

        /**
         * The refresh token.
         */
        String refreshToken
) {
}
