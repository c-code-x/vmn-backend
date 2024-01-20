package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.service.InvitationService;
import com.vdc.vmnbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("invite")
public class InvitationController {
    public InvitationService invitationService;
    public UserService userService;
    //dependency Injection
    public InvitationController(InvitationService invitationService, UserService userService){
        this.invitationService = invitationService;
        this.userService = userService;
    }
    @PostMapping("new")
    public BasicResDTO createInvite(Authentication authentication, @RequestBody @Valid UserInviteReqDTO userInviteReqDTO){
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return invitationService.createRoleBasedInvite(userInviteReqDTO, userDAO);
//        return authentication.getName();
    }

    @GetMapping("verify-invite")
    public ResponseDTO<InvitationDAO> verifyInvite(@RequestParam("token") UUID token){
        return invitationService.verifyInvitation(token);
    }
    @PostMapping("accept-invitation")
    public BasicResDTO createUserByInvitation(@Param ("token") UUID token ,@RequestBody @Valid InviteBasedUserReqDTO inviteBasedUserReqDTO){
       return invitationService.createUserByInvitation(inviteBasedUserReqDTO,token);
    }


}
