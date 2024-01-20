package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.AdminDAO;
import com.vdc.vmnbackend.dao.CoachDAO;
import com.vdc.vmnbackend.dao.MentorDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.AdminRepository;
import com.vdc.vmnbackend.dao.repository.CoachRepository;
import com.vdc.vmnbackend.dao.repository.MentorRepository;
import com.vdc.vmnbackend.dao.repository.UserRepository;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    private final AdminRepository adminRepository;
    private final MentorRepository mentorRepository;
    private final CoachRepository coachRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder, AdminRepository adminRepository, MentorRepository mentorRepository, CoachRepository coachRepository){
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.adminRepository = adminRepository;
        this.mentorRepository = mentorRepository;
        this.coachRepository = coachRepository;
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

    public void createUser(UserSignupReqDTO userSignupReqDTO, UserDAO creatorId){
        if(userRepository.existsByEmailId(userSignupReqDTO.emailId()))
            throw  new ApiRuntimeException("User Already Exists!", HttpStatus.BAD_REQUEST);
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
                    .createdBy(creatorId)
                    .build();
            adminDAO = adminRepository.save(adminDAO);
        }
        else if(userSignupReqDTO.role()== Roles.MENTOR){
            MentorDAO mentorDAO = MentorDAO.builder()
                    .mentorId(userDAO)
                    .adminId(creatorId)
                    .build();
            mentorDAO = mentorRepository.save(mentorDAO);
        }
        else if(userSignupReqDTO.role()== Roles.COACH){
            CoachDAO coachDAO = CoachDAO.builder()
                    .coachId(userDAO)
                    .adminId(creatorId)
                    .build();
            coachDAO = coachRepository.save(coachDAO);
        }
        userRepository.save(userDAO);

    }
}
