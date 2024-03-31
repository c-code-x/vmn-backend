package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Represents a request DTO for inviting a user with provided details.
 */
@Data
public class UserInviteReqDTO {
    /**
     * The name of the user to be invited.
     */
    @NotEmpty
    private String name;

    /**
     * The email address of the user to be invited.
     */
    @Email
    private String emailId;

    /**
     * The role of the user to be invited, which should be either admin or user.
     */
    private Roles role;
}
