package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;

public interface InvitationService {
    public BasicResDTO create_invite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO);
}
