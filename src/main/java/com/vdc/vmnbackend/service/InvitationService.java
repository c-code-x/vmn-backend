package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;

import java.util.UUID;

public interface InvitationService {
    public BasicResDTO createRoleBasedInvite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO);

    public ResponseDTO<InvitationDAO> verifyInvitation(UUID token);

    BasicResDTO createUserByInvitation(InviteBasedUserReqDTO inviteBasedUserReqDTO, UUID token);
}
