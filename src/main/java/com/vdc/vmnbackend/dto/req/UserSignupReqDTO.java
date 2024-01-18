package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;


public record UserSignupReqDTO(

        @NotEmpty
        String name,
        @NotEmpty
        @Email
        String emailId,
        @NotEmpty
        String password,
        @NotEmpty
        Roles role,
        @NotEmpty
        String contact,
        @NotEmpty
        String linkedIn,
        @NotEmpty
        String designation
) {

}
