package com.vdc.vmnbackend.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserInviteReqDTO {
    @NotEmpty
    private String name;
    @Email
    private String emailId;
}
