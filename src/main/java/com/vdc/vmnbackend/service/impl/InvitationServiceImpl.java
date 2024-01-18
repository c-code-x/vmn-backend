package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.InvitationRepository;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.InvitationStatus;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.EmailService;
import com.vdc.vmnbackend.service.InvitationService;
import com.vdc.vmnbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final UserService userService;

    private final EmailService emailService;

    private final int ExpiryHours = 12;
    public InvitationServiceImpl(InvitationRepository invitationRepository, UserService userService, EmailService emailService){

        this.invitationRepository = invitationRepository;
        this.userService = userService;
        this.emailService = emailService;
    }
    public BasicResDTO createInvite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO){
        UserDAO existingUser = userService.getByEmail(userInviteReqDTO.getEmailId());
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setName(userInviteReqDTO.getName());
        invitationDAO.setReceiverMailId(userInviteReqDTO.getEmailId());
        invitationDAO.setSender(userDAO);
        invitationDAO.setToRole(userInviteReqDTO.getRole());
        invitationRepository.save(invitationDAO);
        return emailService.sendEmail(invitationDAO.getReceiverMailId(),"Invitation to VMN Platform","Invitation id: "+invitationDAO.getInvId());


    }


    public ResponseDTO<InvitationDAO> verifyInvitation(String token){
        Optional<InvitationDAO> invitationDAO = invitationRepository.findByInvId(token);
        if(invitationDAO.isEmpty())
            throw new ApiRuntimeException("Invalid Invitation!", HttpStatus.BAD_REQUEST);
        if(invitationDAO.get().getStatus()==InvitationStatus.ACCEPTED)
            throw new ApiRuntimeException("Invitation Already Accepted!", HttpStatus.BAD_REQUEST);
        LocalDateTime current = LocalDateTime.now();
        //check if the invitation is expired or not, expiration is just 12 hours from the created time on invitation
        if(invitationDAO.get().getStatus()==InvitationStatus.EXPIRED)
            throw new ApiRuntimeException("Invitation Expired!", HttpStatus.BAD_REQUEST);
        if(current.isAfter(invitationDAO.get().getCreatedAt().plusHours(12)))
        {
            invitationDAO.get().setStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitationDAO.get());
            throw new ApiRuntimeException("Invitation Expired!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseDTO<InvitationDAO>(invitationDAO.get(), new BasicResDTO("Invitation yet pending!", HttpStatus.OK));
    }


    public BasicResDTO createUserByInvitation(InviteBasedUserReqDTO inviteBasedUserReqDTO) {
        InvitationDAO invitationDAO = invitationRepository.findByInvId(inviteBasedUserReqDTO.token()).get();
        if(invitationDAO.getStatus()==InvitationStatus.ACCEPTED)
            throw new ApiRuntimeException("Invitation Already Accepted!", HttpStatus.BAD_REQUEST);
        if(invitationDAO.getStatus()==InvitationStatus.EXPIRED)
            throw new ApiRuntimeException("Invitation Expired!", HttpStatus.BAD_REQUEST);
        UserSignupReqDTO userSignupReqDTO = new UserSignupReqDTO(inviteBasedUserReqDTO.name(),inviteBasedUserReqDTO.emailId(),inviteBasedUserReqDTO.password(),invitationDAO.getSender().getRole(),inviteBasedUserReqDTO.contact(),inviteBasedUserReqDTO.linkedIn(),
                inviteBasedUserReqDTO.designation(),invitationDAO.getSender().getUid());
        return userService.createUser(userSignupReqDTO);
    }
}
