package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;

import java.util.UUID;

public interface UserService {
    UserDAO getByEmail(String emailId);
    Boolean existsByEmailId(String emailId);
    public void createUser(UserSignupReqDTO userSignupReqDTO, UUID uid);
}
