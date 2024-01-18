package com.vdc.vmnbackend.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;


public record UserSigninReqDTO(
        @Email
        String emailId,
        @NotEmpty
        String password
) {
}
