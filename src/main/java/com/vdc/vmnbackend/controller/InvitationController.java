package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.InvitationRepository;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.service.InvitationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("invite")
public class InvitationController {
    public InvitationService invitationService;
    //dependency Injection
    public InvitationController(InvitationService invitationService){
        this.invitationService = invitationService;
    }
    @PostMapping("create-invite")
    public BasicResDTO create_invite(@RequestBody @Valid UserInviteReqDTO userInviteReqDTO){

        return invitationService.create_invite(userInviteReqDTO, new UserDAO());
    }
}
