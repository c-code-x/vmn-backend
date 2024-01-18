package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;

public interface InvitationService {
    public BasicResDTO createInvite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO);

    public ResponseDTO<InvitationDAO> verifyInvitation(String token);

    BasicResDTO createUserByInvitation(InviteBasedUserReqDTO inviteBasedUserReqDTO);
}
