package com.vdc.vmnbackend.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Represents a request DTO for user sign-in with provided credentials.
 */
public record UserSigninReqDTO(
        /**
         * The email address of the user signing in.
         */
        @Email
        String emailId,

        /**
         * The password of the user signing in.
         */
        @NotEmpty
        String password
) {
}
