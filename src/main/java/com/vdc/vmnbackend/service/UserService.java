package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;

public interface UserService {
    UserDAO getByEmail(String emailId);
    public BasicResDTO createUser(UserSignupReqDTO userSignupReqDTO);
}
