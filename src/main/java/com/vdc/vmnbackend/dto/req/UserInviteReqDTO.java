package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.context.annotation.Role;

@Data
public class UserInviteReqDTO {
    @NotEmpty
    private String name;
    @Email
    private String emailId;

    //role should be either admin or user
    @NotEmpty
    private Roles role;

}
