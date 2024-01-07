package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.InvitationRepository;
import com.vdc.vmnbackend.dao.repository.UserRepository;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.EmailService;
import com.vdc.vmnbackend.service.InvitationService;
import jakarta.persistence.Basic;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvitationServiceImpl implements InvitationService {
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    private final EmailService emailService;
    public InvitationServiceImpl(UserRepository userRepository, InvitationRepository invitationRepository, EmailService emailService){
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }
    public BasicResDTO create_invite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO){
        Optional<UserDAO> existingUser = userRepository.findByEmailId(userInviteReqDTO.getEmailId());
        if(!existingUser.isEmpty())
            return new BasicResDTO("User Already Exists!", HttpStatus.BAD_REQUEST);
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setName(userInviteReqDTO.getName());
        invitationDAO.setReceiverMailId(userInviteReqDTO.getEmailId());
        invitationDAO.setSender(userDAO);
        invitationRepository.save(invitationDAO);
        return emailService.sendEmail(invitationDAO.getReceiverMailId(),"Invitation to VMN Platform","Invitation id: "+invitationDAO.getInvId());


    }
}
