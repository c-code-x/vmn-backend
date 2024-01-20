package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;

import java.util.UUID;

public interface UserService {
    UserDAO getByEmail(String emailId);
    Boolean existsByEmailId(String emailId);
    void createUser(UserSignupReqDTO userSignupReqDTO, UserDAO uid);
//    void roleBasedRepositoryInitialization(UserDAO userDAO, UserDAO creatorId);
}
