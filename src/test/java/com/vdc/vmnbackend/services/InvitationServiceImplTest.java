package com.vdc.vmnbackend.services;

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
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.impl.InvitationServiceImpl;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InvitationServiceImplTest {
    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private VentureDAO ventureDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private InvitationServiceImpl invitationService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test

    void createRoleBasedInvite_ExistingInvitationNotExpired() {

        UserInviteReqDTO userInviteReqDTO = new UserInviteReqDTO();
        userInviteReqDTO.setName("Test User");
        userInviteReqDTO.setEmailId("vvvarun282003@gmail.com");
        userInviteReqDTO.setRole(Roles.ADMIN);


        InvitationDAO existingInvitation = new InvitationDAO();
        existingInvitation.setStatus(InvitationStatus.PENDING);
        existingInvitation.setCreatedAt(LocalDateTime.now().minusHours(1));


        when(invitationRepository.existsByReceiverMailId(userInviteReqDTO.getEmailId())).thenReturn(true);
        when(invitationRepository.findByReceiverMailId("vvvarun282003@gmail.com")).thenReturn(Optional.of(existingInvitation));


        BasicResDTO result = invitationService.createRoleBasedInvite(userInviteReqDTO, new UserDAO());


        assertEquals(CommonConstants.INVITATION_ALREADY_SENT, result.message());
        assertEquals(HttpStatus.BAD_REQUEST, result.status());
    }


    @Test
    void createRoleBasedInvite_ExistingInvitationExpired() {

        UserInviteReqDTO userInviteReqDTO = new UserInviteReqDTO();
        userInviteReqDTO.setName("Test User");
        userInviteReqDTO.setEmailId("test@example.com");
        userInviteReqDTO.setRole(Roles.ADMIN);
        InvitationDAO existingInvitation = new InvitationDAO();
        existingInvitation.setStatus(InvitationStatus.PENDING);
        existingInvitation.setCreatedAt(LocalDateTime.now().minusHours(13));
        when(invitationRepository.findByReceiverMailId("test@example.com")).thenReturn(Optional.of(existingInvitation));


        BasicResDTO result = invitationService.createRoleBasedInvite(userInviteReqDTO, new UserDAO());


        verify(invitationRepository, times(1)).save(any());
    }


    @Test
    void testResendInvite_Unauthorized() {

        UserDAO userDAO = new UserDAO();
        userDAO.setUid(UUID.randomUUID());

        UserDAO sender = new UserDAO();
        sender.setUid(UUID.randomUUID());

        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setSender(sender);


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.resendInvite(invitationDAO, userDAO));

        assertEquals(CommonConstants.UNAUTHORISED_OPERATION, exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
    }

    @Test
    void testResendInvite_InvitationAccepted() {

        UserDAO userDAO = new UserDAO();
        userDAO.setUid(UUID.randomUUID());

        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setSender(userDAO);
        invitationDAO.setStatus(InvitationStatus.ACCEPTED);


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.resendInvite(invitationDAO, userDAO));

        assertEquals(CommonConstants.INVITATION_ACCEPTED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testResendInvite_Success() {

        UserDAO userDAO = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Test User")
                .emailId("abc@gmail.com")
                .role(Roles.ADMIN)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer").build();
        InvitationDAO invitationDAO = InvitationDAO.builder()
                .invId(UUID.randomUUID())
                .sender(userDAO)
                .receiverMailId("receiver@gmail.com")
                .name("Receiver Name")
                .toRole(Roles.ADMIN)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();


        when(invitationRepository.save(invitationDAO)).thenReturn(invitationDAO);
        when(emailService.sendInvitationEmail(invitationDAO, userDAO.getName())).thenReturn(new BasicResDTO("Invitation Sent Successfully", HttpStatus.OK));


        BasicResDTO result = invitationService.resendInvite(invitationDAO, userDAO);


        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.status());

    }
    @Test
    void testCreateMenteeInvite_InvitationExists() {

        UserInviteReqDTO userInviteReqDTO = new UserInviteReqDTO();
        userInviteReqDTO.setEmailId("test@example.com");

        when(invitationService.checkInvitationExistence(userInviteReqDTO.getEmailId())).thenReturn(true);


        BasicResDTO result = invitationService.createMenteeInvite(new VentureDAO(), new UserDAO(), userInviteReqDTO);


        assertEquals(CommonConstants.INVITATION_ALREADY_SENT, result.message());
        assertEquals(HttpStatus.BAD_REQUEST, result.status());
    }
    @Test
    void testCreateMenteeInvite_Unauthorized() {

        UserDAO userDAO = new UserDAO();
        userDAO.setUid(UUID.randomUUID());

        UserDAO coach = new UserDAO();
        coach.setUid(UUID.randomUUID());
        VentureDAO ventureDAO = new VentureDAO();
        ventureDAO.setCoachId(coach);

        UserInviteReqDTO userInviteReqDTO = new UserInviteReqDTO();
        userInviteReqDTO.setEmailId("test@example.com");

        when(invitationService.checkInvitationExistence(userInviteReqDTO.getEmailId())).thenReturn(false);


        BasicResDTO result = invitationService.createMenteeInvite(ventureDAO, userDAO, userInviteReqDTO);


        assertNotNull(result);
        assertEquals(CommonConstants.UNAUTHORISED_OPERATION, result.message());
        assertEquals(HttpStatus.UNAUTHORIZED, result.status());
    }
    @Test
    void testCreateMenteeInvite_UserExists() {

        UserDAO userDAO = new UserDAO();
        userDAO.setUid(UUID.randomUUID());

        VentureDAO ventureDAO = new VentureDAO();
        ventureDAO.setCoachId(userDAO);

        UserInviteReqDTO userInviteReqDTO = new UserInviteReqDTO();
        userInviteReqDTO.setEmailId("test@example.com");

        when(invitationService.checkInvitationExistence(userInviteReqDTO.getEmailId())).thenReturn(false);
        when(userService.existsByEmailId(userInviteReqDTO.getEmailId())).thenReturn(true);


        BasicResDTO result = invitationService.createMenteeInvite(ventureDAO, userDAO, userInviteReqDTO);


        assertEquals(CommonConstants.USER_ALREADY_EXISTS, result.message());
        assertEquals(HttpStatus.BAD_REQUEST, result.status());
    }

    @Test
    void testCreateMenteeInvite_Success() {

        UserDAO userDAO = new UserDAO();
        userDAO.setUid(UUID.randomUUID());

        VentureDAO ventureDAO = new VentureDAO();
        ventureDAO.setCoachId(userDAO);

        UserInviteReqDTO userInviteReqDTO = new UserInviteReqDTO();
        userInviteReqDTO.setEmailId("test@example.com");
        userInviteReqDTO.setName("Test User");
        userInviteReqDTO.setRole(Roles.MENTEE);

        when(invitationService.checkInvitationExistence(userInviteReqDTO.getEmailId())).thenReturn(false);
        when(userService.existsByEmailId(userInviteReqDTO.getEmailId())).thenReturn(false);
        when(emailService.sendInvitationEmail(any(InvitationDAO.class), anyString())).thenReturn(new BasicResDTO("Email sent", HttpStatus.OK));


        BasicResDTO result = invitationService.createMenteeInvite(ventureDAO, userDAO, userInviteReqDTO);


        assertNull(result);
    }
    @Test
    void testVerifyInvitation_InvitationInvalid() {

        UUID token = UUID.randomUUID();
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.empty());


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.verifyInvitation(token));

        assertEquals(CommonConstants.INVITATION_INVALID, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
    @Test
    void testVerifyInvitation_InvitationAccepted() {

        UUID token = UUID.randomUUID();
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setStatus(InvitationStatus.ACCEPTED);
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.of(invitationDAO));


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.verifyInvitation(token));

        assertEquals(CommonConstants.INVITATION_ACCEPTED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
    @Test
    void testVerifyInvitation_InvitationExpired() {

        UUID token = UUID.randomUUID();
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setStatus(InvitationStatus.EXPIRED);
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.of(invitationDAO));


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.verifyInvitation(token));

        assertEquals(CommonConstants.INVITATION_EXPIRED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
    @Test
    void testVerifyInvitation_InvitationPending() {

        UUID token = UUID.randomUUID();
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setStatus(InvitationStatus.PENDING);
        invitationDAO.setCreatedAt(LocalDateTime.now().minusHours(1)); // Set a time within the expiry limit
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.of(invitationDAO));


        ResponseDTO<InvitationDAO> result = invitationService.verifyInvitation(token);


        assertNotNull(result);
        assertEquals(CommonConstants.INVITATION_PENDING, result.res().message());
        assertEquals(HttpStatus.OK, result.res().status());
    }

    @Test
    void testCreateUserByInvitation_InvitationInvalid() {

        UUID token = UUID.randomUUID();
        InviteBasedUserReqDTO inviteBasedUserReqDTO = new InviteBasedUserReqDTO(
                "Test User",
                "test@example.com",
                "password",
                "1234567890",
                "https://www.linkedin.com/in/test",
                "Software Engineer"
        );
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.empty());


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.createUserByInvitation(inviteBasedUserReqDTO, token));

        assertEquals(CommonConstants.INVITATION_INVALID, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
    @Test
    void testCreateUserByInvitation_InvitationAccepted() {

        UUID token = UUID.randomUUID();
        InviteBasedUserReqDTO inviteBasedUserReqDTO = new InviteBasedUserReqDTO("Test User",
                "test@example.com",
                "password",
                "1234567890",
                "https://www.linkedin.com/in/test",
                "Software Engineer");
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setStatus(InvitationStatus.ACCEPTED);
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.of(invitationDAO));


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.createUserByInvitation(inviteBasedUserReqDTO, token));

        assertEquals(CommonConstants.INVITATION_ACCEPTED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }


    @Test
    void testCreateUserByInvitation_InvitationExpired() {

        UUID token = UUID.randomUUID();
        InviteBasedUserReqDTO inviteBasedUserReqDTO = new InviteBasedUserReqDTO("Test User",
                "test@example.com",
                "password",
                "1234567890",
                "https://www.linkedin.com/in/test",
                "Software Engineer");
        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setStatus(InvitationStatus.EXPIRED);
        when(invitationRepository.findByInvId(token)).thenReturn(Optional.of(invitationDAO));


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.createUserByInvitation(inviteBasedUserReqDTO, token));

        assertEquals(CommonConstants.INVITATION_EXPIRED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
    @Test
    void testCreateUserByInvitation_Success() {

        UUID token = UUID.randomUUID();
        InviteBasedUserReqDTO inviteBasedUserReqDTO = new InviteBasedUserReqDTO("Test User",
                "test@example.com",
                "password",
                "1234567890",
                "https://www.linkedin.com/in/test",
                "Software Engineer");

        InvitationDAO invitationDAO = new InvitationDAO();
        invitationDAO.setStatus(InvitationStatus.PENDING);
        invitationDAO.setReceiverMailId("test@example.com");
        invitationDAO.setToRole(Roles.ADMIN);
        invitationDAO.setCreatedAt(LocalDateTime.now().minusHours(1));

        when(invitationRepository.findByInvId(token)).thenReturn(Optional.of(invitationDAO));



        BasicResDTO result = invitationService.createUserByInvitation(inviteBasedUserReqDTO, token);


        assertNotNull(result);
        assertEquals(CommonConstants.USER_CREATED_SUCCESSFULLY, result.message());
        assertEquals(HttpStatus.OK, result.status());
    }

    @Test
    void testGetInvitationByEmailId_InvitationExists() {

        String emailId = "test@example.com";
        InvitationDAO invitationDAO = new InvitationDAO();
        when(invitationRepository.findByReceiverMailId(emailId)).thenReturn(Optional.of(invitationDAO));


        InvitationDAO result = invitationService.getInvitationByEmailId(emailId);


        assertNotNull(result);
        assertEquals(invitationDAO, result);
    }

    @Test
    void testGetInvitationByEmailId_InvitationDoesNotExist() {

        String emailId = "test@example.com";
        when(invitationRepository.findByReceiverMailId(emailId)).thenReturn(Optional.empty());


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.getInvitationByEmailId(emailId));

        assertEquals(CommonConstants.INVITATION_INVALID, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testGetInvitationByInvId_InvitationExists() {

        UUID invId = UUID.randomUUID();
        InvitationDAO invitationDAO = new InvitationDAO();
        when(invitationRepository.findByInvId(invId)).thenReturn(Optional.of(invitationDAO));


        InvitationDAO result = invitationService.getInvitationByInvId(invId);


        assertNotNull(result);
        assertEquals(invitationDAO, result);
    }

    @Test
    void testGetInvitationByInvId_InvitationDoesNotExist() {

        UUID invId = UUID.randomUUID();
        when(invitationRepository.findByInvId(invId)).thenReturn(Optional.empty());


        ApiRuntimeException exception = assertThrows(ApiRuntimeException.class, () ->
                invitationService.getInvitationByInvId(invId));

        assertEquals(CommonConstants.INVITATION_INVALID, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}
