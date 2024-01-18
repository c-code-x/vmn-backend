package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.UserRepository;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
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

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder){
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
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

    public void createUser(UserSignupReqDTO userSignupReqDTO, UUID creatorId){
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
                .createdBy(creatorId)
                .build();
        userRepository.save(userDAO);
    }
}
