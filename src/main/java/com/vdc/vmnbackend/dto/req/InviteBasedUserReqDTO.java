package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;


public record InviteBasedUserReqDTO(
        @NotEmpty
        String name,
        @NotEmpty
                @Email
        String emailId,
        @NotEmpty
        String password,
        @NotEmpty
        String contact,
        @NotEmpty
        String linkedIn,
        @NotEmpty
        String designation

) {
}
