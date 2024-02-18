package com.vdc.vmnbackend.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * Represents a request DTO for inviting a user based on provided details.
 */
public record InviteBasedUserReqDTO(
        /**
         * The name of the user.
         */
        @NotEmpty
        String name,
        
        /**
         * The email address of the user.
         */
        @NotEmpty
        @Email
        String emailId,
        
        /**
         * The password for the user.
         */
        @NotEmpty
        String password,
        
        /**
         * The contact information of the user.
         */
        @NotEmpty
        String contact,
        
        /**
         * The LinkedIn profile of the user.
         */
        @NotEmpty
        String linkedIn,
        
        /**
         * The designation or role of the user.
         */
        @NotEmpty
        String designation
) {
}
