package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.InvitationService;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.VentureService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("invite")
public class InvitationController {
    public InvitationService invitationService;
    public UserService userService;

    public VentureService ventureService;
    //dependency Injection
    public InvitationController(InvitationService invitationService, UserService userService, VentureService ventureService) {
        this.invitationService = invitationService;
        this.userService = userService;
        this.ventureService = ventureService;
    }
    @PostMapping("new")
    public BasicResDTO createInvite(Authentication authentication, @RequestBody @Valid UserInviteReqDTO userInviteReqDTO) {
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return invitationService.createRoleBasedInvite(userInviteReqDTO, userDAO);
//        return authentication.getName();
    }
    @PostMapping("resend/{invId}")
    public BasicResDTO resendInvite(Authentication authentication, @PathVariable UUID invId){
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return invitationService.resendInvite(invitationService.getInvitationByInvId(invId), userDAO);
    }

    @GetMapping("verifyInvite/{token}")
    public ResponseDTO<InvitationDAO> verifyInvite(@PathVariable UUID token){
        return invitationService.verifyInvitation(token);
    }
    @PostMapping("acceptInvitation/{token}")
    public BasicResDTO createUserByInvitation(@PathVariable UUID token ,@RequestBody @Valid InviteBasedUserReqDTO inviteBasedUserReqDTO){
       return invitationService.createUserByInvitation(inviteBasedUserReqDTO,token);
    }
    @PostMapping("new/mentee/{ventureId}")
    public BasicResDTO createMenteeInvite(@PathVariable UUID ventureId, @RequestBody @Valid UserInviteReqDTO userInviteReqDTO, Authentication authentication){
        UserDAO invitedBy = userService.getByEmail(authentication.getName());
        Optional<VentureDAO> ventureDAO = ventureService.getVentureById(ventureId);
        if(ventureDAO.isEmpty())
            throw new ApiRuntimeException("Venture not found", HttpStatus.NOT_FOUND);
        return invitationService.createMenteeInvite(ventureDAO.get(), userService.getByEmail(authentication.getName()), userInviteReqDTO);

    }
}
