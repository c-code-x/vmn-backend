package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ProfileDataResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.Campus;

import java.util.UUID;

public interface UserService {
    UserDAO getByEmail(String emailId);
    Boolean existsByEmailId(String emailId);
    void createUser(UserSignupReqDTO userSignupReqDTO, InvitationDAO invitationDAO);
    Campus getCampus(UserDAO userDAO);

    ProfileDataResDTO getProfile(UserDAO userDAO);

    BasicResDTO updateProfile(UserDAO byEmail, ProfileDataResDTO<?> profileDataResDTO);
//    void roleBasedRepositoryInitialization(UserDAO userDAO, UserDAO creatorId);
}
