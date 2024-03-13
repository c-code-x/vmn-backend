package com.vdc.vmnbackend.services;

import com.vdc.vmnbackend.dao.*;
import com.vdc.vmnbackend.dao.repository.*;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ProfileDataResDTO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.InvitationStatus;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.impl.UserServiceImpl;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bcryptEncoder;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private MentorRepository mentorRepository;
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private MenteeRepository menteeRepository;

    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, bcryptEncoder, adminRepository, mentorRepository, coachRepository, menteeRepository);
    }

    @Test
    public void testGetByEmail() {
        UserDAO userDAO = new UserDAO();
        userDAO.setEmailId("test@example.com");
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(userDAO));

        UserDAO result = userService.getByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmailId());
    }

    @Test
    public void testGetByEmail_NotFound() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());

        assertThrows(ApiRuntimeException.class, () -> userService.getByEmail("test@example.com"));
    }

    @Test
    public void testExistsByEmailId() {
        when(userRepository.existsByEmailId(anyString())).thenReturn(true);

        Boolean result = userService.existsByEmailId("test@example.com");

        assertTrue(result);
    }

    @Test
    public void testCreateUser() {
        when(userRepository.existsByEmailId(anyString())).thenReturn(false);
        when(userRepository.save(any(UserDAO.class))).thenReturn(new UserDAO());
        when(adminRepository.save(any(AdminDAO.class))).thenReturn(new AdminDAO());

        UserSignupReqDTO userSignupReqDTO = new UserSignupReqDTO(
                "Test User",
                "test@example.com",
                "password",
                Roles.ADMIN,
                "1234567890",
                "linkedin.com/test",
                "Test Designation"
        );
        UserDAO sender = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Sender User")
                .emailId("sender@gmail.com")
                .role(Roles.USER)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer")
                .build();
        InvitationDAO invitation = InvitationDAO.builder()
                .invId(UUID.randomUUID())
                .sender(sender)
                .receiverMailId("receiver@gmail.com")
                .name("Receiver Name")
                .toRole(Roles.ADMIN)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> userService.createUser(userSignupReqDTO, invitation));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        when(userRepository.existsByEmailId(anyString())).thenReturn(true);

        UserSignupReqDTO userSignupReqDTO = new UserSignupReqDTO(
                "Test User",
                "test@example.com",
                "password",
                Roles.ADMIN,
                "1234567890",
                "linkedin.com/test",
                "Test Designation"
        );
        UserDAO sender = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Sender User")
                .emailId("sender@gmail.com")
                .role(Roles.USER)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer")
                .build();
        InvitationDAO invitation = InvitationDAO.builder()
                .invId(UUID.randomUUID())
                .sender(sender)
                .receiverMailId("receiver@gmail.com")
                .name("Receiver Name")
                .toRole(Roles.ADMIN)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        assertThrows(ApiRuntimeException.class, () -> userService.createUser(userSignupReqDTO, invitation));
    }

    @Test
    public void testGetCampus_Admin() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.ADMIN);
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.setCampus(Campus.Bengaluru);
        when(adminRepository.findByAdminId(any(UserDAO.class))).thenReturn(adminDAO);

        Campus result = userService.getCampus(userDAO);

        assertEquals(Campus.Bengaluru, result);
    }

    @Test
    public void testGetCampus_Coach() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.COACH);
        CoachDAO coachDAO = new CoachDAO();
        coachDAO.setCampus(Campus.Bengaluru);
        when(coachRepository.findByCoachId(any(UserDAO.class))).thenReturn(coachDAO);

        Campus result = userService.getCampus(userDAO);

        assertEquals(Campus.Bengaluru, result);
    }

    @Test
    public void testGetCampus_NotFound() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.MENTOR);

        assertThrows(ApiRuntimeException.class, () -> userService.getCampus(userDAO));
    }

    @Test
    public void testGetProfile_Admin() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.ADMIN);
        AdminDAO adminDAO = new AdminDAO();
        when(adminRepository.findByAdminId(any(UserDAO.class))).thenReturn(adminDAO);

        ProfileDataResDTO<?> result = userService.getProfile(userDAO);

        assertNotNull(result);
        assertEquals(userDAO, result.userDAO());
        assertEquals(adminDAO, result.roleSpecificData());
    }

    @Test
    public void testGetProfile_Coach() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.COACH);
        CoachDAO coachDAO = new CoachDAO();
        when(coachRepository.findByCoachId(any(UserDAO.class))).thenReturn(coachDAO);

        ProfileDataResDTO<?> result = userService.getProfile(userDAO);

        assertNotNull(result);
        assertEquals(userDAO, result.userDAO());
        assertEquals(coachDAO, result.roleSpecificData());
    }

    @Test
    public void testGetProfile_Mentor() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.MENTOR);
        MentorDAO mentorDAO = new MentorDAO();
        when(mentorRepository.findByMentorId(any(UserDAO.class))).thenReturn(mentorDAO);

        ProfileDataResDTO<?> result = userService.getProfile(userDAO);

        assertNotNull(result);
        assertEquals(userDAO, result.userDAO());
        assertEquals(mentorDAO, result.roleSpecificData());
    }

    @Test
    public void testGetProfile_Mentee() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.MENTEE);
        MenteeDAO menteeDAO = new MenteeDAO();
        when(menteeRepository.findMenteeDAOByMenteeId(any(UserDAO.class))).thenReturn(menteeDAO);

        ProfileDataResDTO<?> result = userService.getProfile(userDAO);

        assertNotNull(result);
        assertEquals(userDAO, result.userDAO());
        assertEquals(menteeDAO, result.roleSpecificData());
    }

    @Test
    public void testGetProfile_NotFound() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.USER);

        assertThrows(ApiRuntimeException.class, () -> userService.getProfile(userDAO));
    }

    @Test
    public void testUpdateProfile_Admin() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.ADMIN);
        AdminDAO adminDAO = new AdminDAO();
        when(adminRepository.findByAdminId(any(UserDAO.class))).thenReturn(adminDAO);
        when(adminRepository.save(any(AdminDAO.class))).thenReturn(adminDAO);
        when(userRepository.save(any(UserDAO.class))).thenReturn(userDAO);

        ProfileDataResDTO<AdminDAO> profileDataResDTO = new ProfileDataResDTO<>(userDAO, adminDAO);

        BasicResDTO result = userService.updateProfile(userDAO, profileDataResDTO);

        assertNotNull(result);
        assertEquals(CommonConstants.PROFILE_UPDATED, result.message());
    }

    @Test
    public void testUpdateProfile_Coach() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.COACH);
        CoachDAO coachDAO = new CoachDAO();
        when(coachRepository.findByCoachId(any(UserDAO.class))).thenReturn(coachDAO);
        when(coachRepository.save(any(CoachDAO.class))).thenReturn(coachDAO);
        when(userRepository.save(any(UserDAO.class))).thenReturn(userDAO);

        ProfileDataResDTO<CoachDAO> profileDataResDTO = new ProfileDataResDTO<>(userDAO, coachDAO);

        BasicResDTO result = userService.updateProfile(userDAO, profileDataResDTO);

        assertNotNull(result);
        assertEquals(CommonConstants.PROFILE_UPDATED, result.message());
    }

    @Test
    public void testUpdateProfile_Mentor() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.MENTOR);
        MentorDAO mentorDAO = new MentorDAO();
        when(mentorRepository.findByMentorId(any(UserDAO.class))).thenReturn(mentorDAO);
        when(mentorRepository.save(any(MentorDAO.class))).thenReturn(mentorDAO);
        when(userRepository.save(any(UserDAO.class))).thenReturn(userDAO);

        ProfileDataResDTO<MentorDAO> profileDataResDTO = new ProfileDataResDTO<>(userDAO, mentorDAO);

        BasicResDTO result = userService.updateProfile(userDAO, profileDataResDTO);

        assertNotNull(result);
        assertEquals(CommonConstants.PROFILE_UPDATED, result.message());
    }

    @Test
    public void testUpdateProfile_Mentee() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.MENTEE);
        MenteeDAO menteeDAO = new MenteeDAO();
        when(menteeRepository.findMenteeDAOByMenteeId(any(UserDAO.class))).thenReturn(menteeDAO);
        when(menteeRepository.save(any(MenteeDAO.class))).thenReturn(menteeDAO);
        when(userRepository.save(any(UserDAO.class))).thenReturn(userDAO);

        ProfileDataResDTO<MenteeDAO> profileDataResDTO = new ProfileDataResDTO<>(userDAO, menteeDAO);

        BasicResDTO result = userService.updateProfile(userDAO, profileDataResDTO);

        assertNotNull(result);
        assertEquals(CommonConstants.PROFILE_UPDATED, result.message());
    }

    @Test
    public void testUpdateProfile_NotFound() {
        UserDAO userDAO = new UserDAO();
        userDAO.setRole(Roles.USER);
        UserDAO updatedUserDAO = new UserDAO();
        ProfileDataResDTO<UserDAO> profileDataResDTO = new ProfileDataResDTO<>(updatedUserDAO, updatedUserDAO);

        assertThrows(ApiRuntimeException.class, () -> userService.updateProfile(userDAO, profileDataResDTO));
    }

}