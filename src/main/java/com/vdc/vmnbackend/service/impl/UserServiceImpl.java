package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.*;
import com.vdc.vmnbackend.dao.repository.*;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ProfileDataResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    private final AdminRepository adminRepository;
    private final MentorRepository mentorRepository;
    private final CoachRepository coachRepository;
    private final MenteeRepository menteeRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder, AdminRepository adminRepository, MentorRepository mentorRepository, CoachRepository coachRepository, MenteeRepository menteeRepository){
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.adminRepository = adminRepository;
        this.mentorRepository = mentorRepository;
        this.coachRepository = coachRepository;
        this.menteeRepository = menteeRepository;
    }
    public UserDAO getByEmail(String emailId){
        Optional<UserDAO> userDAOOptional = userRepository.findByEmailId(emailId);
        if(userDAOOptional.isPresent()){
            return userDAOOptional.get();
        } else {
            throw new ApiRuntimeException("User with email " + emailId + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Boolean existsByEmailId(String emailId) {
        return userRepository.existsByEmailId(emailId);
    }

    public void createUser(UserSignupReqDTO userSignupReqDTO, InvitationDAO invitationDAO){
        if(userRepository.existsByEmailId(userSignupReqDTO.emailId()))
            throw  new ApiRuntimeException(CommonConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        UserDAO userDAO = UserDAO.builder()
                .emailId(userSignupReqDTO.emailId())
                .name(userSignupReqDTO.name())
                .role(userSignupReqDTO.role())
                .password(bcryptEncoder.encode(userSignupReqDTO.password()))
                .contact(userSignupReqDTO.contact())
                .linkedIn(userSignupReqDTO.linkedIn())
                .designation(userSignupReqDTO.designation())
                .build();

        if(userSignupReqDTO.role()== Roles.ADMIN){
            AdminDAO adminDAO = AdminDAO.builder()
                    .adminId(userDAO)
                    .createdBy(invitationDAO.getSender())
                    .build();
            adminDAO = adminRepository.save(adminDAO);
        }
        else if(userSignupReqDTO.role()== Roles.MENTOR){
            MentorDAO mentorDAO = MentorDAO.builder()
                    .mentorId(userDAO)
                    .adminId(invitationDAO.getSender())
                    .build();
            mentorDAO = mentorRepository.save(mentorDAO);
        }
        else if(userSignupReqDTO.role()== Roles.COACH){
            CoachDAO coachDAO = CoachDAO.builder()
                    .coachId(userDAO)
                    .adminId(invitationDAO.getSender())
                    .build();
            coachDAO = coachRepository.save(coachDAO);
        }
        else if (userSignupReqDTO.role()==Roles.MENTEE){
            MenteeDAO menteeDAO = MenteeDAO.builder()
                    .menteeId(userDAO)
                    .ventureId(invitationDAO.getVenture())
                    .build();
            menteeDAO = menteeRepository.save(menteeDAO);
        }
        else{
            throw new ApiRuntimeException("Invalid Role!", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(userDAO);

    }
    public Campus getCampus(UserDAO userDAO){
        if(userDAO.getRole()==Roles.ADMIN){
            AdminDAO adminDAO = adminRepository.findByAdminId(userDAO);
            return adminDAO.getCampus();
        }
        else if(userDAO.getRole()==Roles.COACH){
            CoachDAO coachDAO = coachRepository.findByCoachId(userDAO);
            return coachDAO.getCampus();
        }
        else{
            throw new ApiRuntimeException("User with email not found! Campus fetch failed!", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ProfileDataResDTO<?> getProfile(UserDAO userDAO) {
        if(userDAO.getRole()==Roles.ADMIN){
            AdminDAO adminDAO = adminRepository.findByAdminId(userDAO);
            return new ProfileDataResDTO<AdminDAO>(userDAO, adminDAO);
        }
        else if(userDAO.getRole()==Roles.COACH){
            CoachDAO coachDAO = coachRepository.findByCoachId(userDAO);
            return new ProfileDataResDTO<CoachDAO>(userDAO, coachDAO);
        }
        else if(userDAO.getRole()==Roles.MENTOR){
            MentorDAO mentorDAO = mentorRepository.findByMentorId(userDAO);
            return new ProfileDataResDTO<MentorDAO>(userDAO, mentorDAO);
        } else if(userDAO.getRole()==Roles.MENTEE) {
            MenteeDAO menteeDAO = menteeRepository.findMenteeDAOByMenteeId(userDAO);
            return new ProfileDataResDTO<MenteeDAO>(userDAO, menteeDAO);
        } else{
            throw new ApiRuntimeException(CommonConstants.PROFILE_FETCH_FAILER, HttpStatus.NOT_FOUND);
        }
    }


    public BasicResDTO updateProfile(UserDAO userDAO, ProfileDataResDTO<?> profileDataResDTO) {
        userDAO.setName(profileDataResDTO.userDAO().getName());
        userDAO.setContact(profileDataResDTO.userDAO().getContact());
        userDAO.setLinkedIn(profileDataResDTO.userDAO().getLinkedIn());
        userDAO.setDesignation(profileDataResDTO.userDAO().getDesignation());
        userDAO.setPassword(profileDataResDTO.userDAO().getPassword());

        if(userDAO.getRole()==Roles.ADMIN){
            AdminDAO adminDAO = adminRepository.findByAdminId(userDAO);
            AdminDAO updatedAdminDAO = (AdminDAO) profileDataResDTO.roleSpecificData();
            adminDAO.setCampus(updatedAdminDAO.getCampus());
            adminDAO.setBio(updatedAdminDAO.getBio());
            adminDAO = adminRepository.save(adminDAO);
        }
        else if(userDAO.getRole()==Roles.COACH){
            CoachDAO coachDAO = coachRepository.findByCoachId(userDAO);
            CoachDAO updatedCoachDAO = (CoachDAO) profileDataResDTO.roleSpecificData();
            coachDAO.setCampus(updatedCoachDAO.getCampus());
            coachDAO.setBio(updatedCoachDAO.getBio());
            coachDAO = coachRepository.save(coachDAO);
        }
        else if(userDAO.getRole()==Roles.MENTOR){
            MentorDAO mentorDAO = mentorRepository.findByMentorId(userDAO);
            MentorDAO updatedMentorDAO = (MentorDAO) profileDataResDTO.roleSpecificData();
            mentorDAO.setBio(updatedMentorDAO.getBio());
            mentorDAO.setExpertise(updatedMentorDAO.getExpertise());
            mentorDAO.setExperience(updatedMentorDAO.getExperience());
            mentorDAO = mentorRepository.save(mentorDAO);
        } else if(userDAO.getRole()==Roles.MENTEE) {
            MenteeDAO menteeDAO = menteeRepository.findMenteeDAOByMenteeId(userDAO);
            MenteeDAO updatedMenteeDAO = (MenteeDAO) profileDataResDTO.roleSpecificData();
            menteeDAO.setCampus(updatedMenteeDAO.getCampus());
            menteeDAO = menteeRepository.save(menteeDAO);
        } else{
            throw new ApiRuntimeException(CommonConstants.PROFILE_FETCH_FAILER, HttpStatus.NOT_FOUND);
        }
        userRepository.save(userDAO);
        return new BasicResDTO(CommonConstants.PROFILE_UPDATED, HttpStatus.OK);
    }
}
