package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.UserRepository;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserDAO getByEmail(String emailId){
        UserDAO userDAO = userRepository.findByEmailId(emailId).get();
        if (userDAO == null)
            throw new ApiRuntimeException("User does not exist!", HttpStatus.NOT_FOUND);
        return userDAO;
    }

    public BasicResDTO createUser(UserSignupReqDTO userSignupReqDTO){
        if(userRepository.findByEmailId(userSignupReqDTO.emailId()).isPresent())
            return new BasicResDTO("User Already Exists!", HttpStatus.BAD_REQUEST);
        userRepository.save(userSignupReqDTO.toUserDAO());
        return new BasicResDTO("User Created Successfully!", HttpStatus.OK);
    }
}
