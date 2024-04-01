package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Represents a request DTO for user sign-up with required user details.
 */
public record UserSignupReqDTO(
        /**
         * The name of the user signing up.
         */
        @NotEmpty
        String name,

        /**
         * The email address of the user signing up.
         */
        @NotEmpty
        @Email
        String emailId,

        /**
         * The password of the user signing up.
         */
        @NotEmpty
        String password,

        /**
         * The role of the user signing up.
         */
        @NotEmpty
        Roles role,

        /**
         * The contact information of the user signing up.
         */
        @NotEmpty
        String contact,

        /**
         * The LinkedIn profile link of the user signing up.
         */
        @NotEmpty
        String linkedIn,

        /**
         * The designation or job title of the user signing up.
         */
        @NotEmpty
        String designation
) {
}
