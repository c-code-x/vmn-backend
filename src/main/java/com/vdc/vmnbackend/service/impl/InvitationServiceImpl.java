package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dao.repository.InvitationRepository;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.InvitationStatus;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.EmailService;
import com.vdc.vmnbackend.service.InvitationService;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    public BasicResDTO createRoleBasedInvite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO){
        if (invitationExistsByEmailId(userInviteReqDTO.getEmailId()))
            return new BasicResDTO(CommonConstants.INVITATION_ALREADY_SENT, HttpStatus.BAD_REQUEST);
        Boolean existingUser = userService.existsByEmailId(userInviteReqDTO.getEmailId());
        if (existingUser)
            return new BasicResDTO(CommonConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        if(userInviteReqDTO.getRole().equals(Roles.ADMIN) || userInviteReqDTO.getRole().equals(Roles.COACH) || userInviteReqDTO.getRole().equals(Roles.MENTOR))
        {
            if(userDAO.getRole().equals(userInviteReqDTO.getRole()))
                return new BasicResDTO(CommonConstants.INVITATION_TO_SAMEROLE, HttpStatus.BAD_REQUEST);
            InvitationDAO invitationDAO = new InvitationDAO();
            invitationDAO.setName(userInviteReqDTO.getName());
            invitationDAO.setReceiverMailId(userInviteReqDTO.getEmailId());
            invitationDAO.setSender(userDAO);
            invitationDAO.setToRole(userInviteReqDTO.getRole());
            invitationRepository.save(invitationDAO);
            return emailService.sendInvitationEmail(invitationDAO, userDAO.getName());

        }
        return new BasicResDTO(CommonConstants.INVITATION_INVALID_ROLE, HttpStatus.BAD_REQUEST);

    }
    public BasicResDTO resendInvite(UUID invId, UserDAO userDAO){
        Optional<InvitationDAO> invitationDAO = invitationRepository.findByInvId(invId);
        if(invitationDAO.isEmpty())
            throw new ApiRuntimeException(CommonConstants.INVITATION_INVALID, HttpStatus.BAD_REQUEST);
        if(!invitationDAO.get().getSender().equals(userDAO))
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        if (invitationDAO.get().getStatus()==InvitationStatus.ACCEPTED)
            throw new ApiRuntimeException(CommonConstants.INVITATION_ACCEPTED, HttpStatus.BAD_REQUEST);
        return emailService.sendInvitationEmail(invitationDAO.get(), userDAO.getName());
    }
    public BasicResDTO createMenteeInvite(VentureDAO ventureId, UserDAO userDAO, UserInviteReqDTO userInviteReqDTO) {
        if (invitationExistsByEmailId(userInviteReqDTO.getEmailId()))
            return new BasicResDTO(CommonConstants.INVITATION_ALREADY_SENT, HttpStatus.BAD_REQUEST);
        if(!ventureId.getCoachId().equals(userDAO))
            return new BasicResDTO(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        Boolean existingUser = userService.existsByEmailId(userInviteReqDTO.getEmailId());

        if (existingUser)
            return new BasicResDTO(CommonConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        InvitationDAO invitationDAO = InvitationDAO.builder()
                .name(userInviteReqDTO.getName())
                .receiverMailId(userInviteReqDTO.getEmailId())
                .sender(userDAO)
                .toRole(Roles.MENTEE)
                .venture(ventureId)
                .build();
        invitationRepository.save(invitationDAO);
        return emailService.sendInvitationEmail(invitationDAO, userDAO.getName());
    }


    public ResponseDTO<InvitationDAO> verifyInvitation(UUID token){
        Optional<InvitationDAO> invitationDAO = invitationRepository.findByInvId(token);
        if(invitationDAO.isEmpty())
            throw new ApiRuntimeException(CommonConstants.INVITATION_INVALID, HttpStatus.BAD_REQUEST);
        if(invitationDAO.get().getStatus()==InvitationStatus.ACCEPTED)
            throw new ApiRuntimeException(CommonConstants.INVITATION_ACCEPTED, HttpStatus.BAD_REQUEST);
        LocalDateTime current = LocalDateTime.now();
        //check if the invitation is expired or not, expiration is just 12 hours from the created time on invitation
        if(invitationDAO.get().getStatus()==InvitationStatus.EXPIRED)
            throw new ApiRuntimeException(CommonConstants.INVITATION_EXPIRED, HttpStatus.BAD_REQUEST);
        if(current.isAfter(invitationDAO.get().getCreatedAt().plusHours(12)))
        {
            invitationDAO.get().setStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitationDAO.get());
            throw new ApiRuntimeException(CommonConstants.INVITATION_EXPIRED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseDTO<InvitationDAO>(invitationDAO.get(), new BasicResDTO(CommonConstants.INVITATION_PENDING, HttpStatus.OK));
    }


    public BasicResDTO createUserByInvitation(InviteBasedUserReqDTO inviteBasedUserReqDTO, UUID token) {
        Optional<InvitationDAO> invitationDAO = invitationRepository.findByInvId(token);
        if(invitationDAO.isEmpty())
            throw new ApiRuntimeException(CommonConstants.INVITATION_INVALID, HttpStatus.BAD_REQUEST);
        if(invitationDAO.get().getStatus()==InvitationStatus.ACCEPTED)
            throw new ApiRuntimeException(CommonConstants.INVITATION_ACCEPTED, HttpStatus.BAD_REQUEST);
        if(invitationDAO.get().getStatus()==InvitationStatus.EXPIRED)
            throw new ApiRuntimeException(CommonConstants.INVITATION_EXPIRED, HttpStatus.BAD_REQUEST);
        var current = LocalDateTime.now();
        if(current.isAfter(invitationDAO.get().getCreatedAt().plusHours(12)))
        {
            invitationDAO.get().setStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitationDAO.get());
            throw new ApiRuntimeException(CommonConstants.INVITATION_EXPIRED, HttpStatus.BAD_REQUEST);
        }
        try {
            UserSignupReqDTO userSignupReqDTO = new UserSignupReqDTO(inviteBasedUserReqDTO.name(), invitationDAO.get().getReceiverMailId(), inviteBasedUserReqDTO.password(), invitationDAO.get().getToRole(), inviteBasedUserReqDTO.contact(), inviteBasedUserReqDTO.linkedIn(),
                    inviteBasedUserReqDTO.designation());

            userService.createUser(userSignupReqDTO, invitationDAO.get());
            invitationDAO.get().setStatus(InvitationStatus.ACCEPTED);

            invitationRepository.save(invitationDAO.get());

            return new BasicResDTO(CommonConstants.USER_CREATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResDTO("Error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    boolean invitationExistsByEmailId(String emailId) {
        return invitationRepository.existsByReceiverMailId(emailId);
    }


}
