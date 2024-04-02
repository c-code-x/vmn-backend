package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing invitations.
 */
public interface InvitationService {

    /**
     * Creates a role-based invitation for a user.
     *
     * @param userInviteReqDTO The request containing details of the user to be invited.
     * @param userDAO          The UserDAO of the inviter.
     * @return A BasicResDTO indicating the result of the operation.
     */
    public BasicResDTO createRoleBasedInvite(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO);

    /**
     * Verifies an invitation based on the provided token.
     *
     * @param token The token to verify the invitation.
     * @return A ResponseDTO containing the InvitationDAO if the token is valid.
     */
    public ResponseDTO<InvitationDAO> verifyInvitation(UUID token);

    /**
     * Creates a user based on the invitation.
     *
     * @param inviteBasedUserReqDTO The request containing details of the invited user.
     * @param token                 The token associated with the invitation.
     * @return A BasicResDTO indicating the result of the operation.
     */
    BasicResDTO createUserByInvitation(InviteBasedUserReqDTO inviteBasedUserReqDTO, UUID token);

    /**
     * Resends an invitation to the specified user.
     *
     * @param invitationDAO The InvitationDAO to resend.
     * @param userDAO       The UserDAO of the inviter.
     * @return A BasicResDTO indicating the result of the operation.
     */
    BasicResDTO resendInvite(InvitationDAO invitationDAO, UserDAO userDAO);

    /**
     * Retrieves an invitation based on the email ID of the recipient.
     *
     * @param emailId The email ID of the recipient.
     * @return The InvitationDAO associated with the email ID.
     */
    InvitationDAO getInvitationByEmailId(String emailId);

    /**
     * Retrieves an invitation based on its unique identifier.
     *
     * @param invId The UUID of the invitation.
     * @return The InvitationDAO associated with the UUID.
     */
    public InvitationDAO getInvitationByInvId(UUID invId);

    /**
     * Creates an invitation for a mentee.
     *
     * @param ventureId       The VentureDAO associated with the invitation.
     * @param userDAO         The UserDAO of the inviter.
     * @param userInviteReqDTO The request containing details of the invited user.
     * @return A BasicResDTO indicating the result of the operation.
     */
    BasicResDTO createMenteeInvite(VentureDAO ventureId, UserDAO userDAO, UserInviteReqDTO userInviteReqDTO);

    ResponseDTO<List<InvitationDAO>> getInvitations(UserDAO userDAO);
}
