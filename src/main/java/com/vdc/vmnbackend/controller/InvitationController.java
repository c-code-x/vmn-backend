package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.InvitationService;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.VentureService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller class for managing invitation-related operations.
 */
@RestController
@RequestMapping("invite")
public class InvitationController {

    private final InvitationService invitationService;
    private final UserService userService;
    private final VentureService ventureService;

    /**
     * Constructor for InvitationController class.
     *
     * @param invitationService The InvitationService for managing invitation-related operations.
     * @param userService       The UserService for managing user-related operations.
     * @param ventureService    The VentureService for managing venture-related operations.
     */
    public InvitationController(InvitationService invitationService, UserService userService, VentureService ventureService) {
        this.invitationService = invitationService;
        this.userService = userService;
        this.ventureService = ventureService;
    }

    @GetMapping
    public ResponseDTO<List<InvitationDAO>> getInvitations(Authentication authentication) {
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return invitationService.getInvitations(userDAO);
    }

    /**
     * Endpoint for creating a new invitation.
     *
     * @param authentication   The authentication object representing the logged-in user.
     * @param userInviteReqDTO The UserInviteReqDTO containing details of the invitation to be created.
     * @return A BasicResDTO indicating the result of the invitation creation operation.
     */
    @PostMapping("new")
    public BasicResDTO createInvite(Authentication authentication, @RequestBody @Valid UserInviteReqDTO userInviteReqDTO) {
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return invitationService.createRoleBasedInvite(userInviteReqDTO, userDAO);
    }

    /**
     * Endpoint for resending an invitation.
     *
     * @param authentication The authentication object representing the logged-in user.
     * @param invId           The UUID of the invitation to be resent.
     * @return A BasicResDTO indicating the result of the invitation resend operation.
     */
    @PostMapping("resend/{invId}")
    public BasicResDTO resendInvite(Authentication authentication, @PathVariable UUID invId) {
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return invitationService.resendInvite(invitationService.getInvitationByInvId(invId), userDAO);
    }

    /**
     * Endpoint for verifying an invitation.
     *
     * @param token The UUID token of the invitation to be verified.
     * @return A ResponseDTO containing the InvitationDAO object representing the verified invitation.
     */
    @GetMapping("verifyInvite/{token}")
    public ResponseDTO<InvitationDAO> verifyInvite(@PathVariable UUID token) {
        return invitationService.verifyInvitation(token);
    }

    /**
     * Endpoint for creating a user by accepting an invitation.
     *
     * @param token                The UUID token of the invitation being accepted.
     * @param inviteBasedUserReqDTO The InviteBasedUserReqDTO containing details of the user accepting the invitation.
     * @return A BasicResDTO indicating the result of the user creation operation.
     */
    @PostMapping("acceptInvitation/{token}")
    public BasicResDTO createUserByInvitation(@PathVariable UUID token, @RequestBody @Valid InviteBasedUserReqDTO inviteBasedUserReqDTO) {
        return invitationService.createUserByInvitation(inviteBasedUserReqDTO, token);
    }

    /**
     * Endpoint for creating a mentee invitation for a specific venture.
     *
     * @param ventureId         The UUID of the venture for which the mentee invitation is being created.
     * @param userInviteReqDTO  The UserInviteReqDTO containing details of the invitation to be created.
     * @param authentication    The authentication object representing the logged-in user.
     * @return A BasicResDTO indicating the result of the mentee invitation creation operation.
     */
    @PostMapping("new/mentee/{ventureId}")
    public BasicResDTO createMenteeInvite(@PathVariable UUID ventureId, @RequestBody @Valid UserInviteReqDTO userInviteReqDTO, Authentication authentication) {
        UserDAO invitedBy = userService.getByEmail(authentication.getName());
        Optional<VentureDAO> ventureDAO = ventureService.getVentureById(ventureId);
        if (ventureDAO.isEmpty())
            throw new ApiRuntimeException("Venture not found", HttpStatus.NOT_FOUND);
        return invitationService.createMenteeInvite(ventureDAO.get(), userService.getByEmail(authentication.getName()), userInviteReqDTO);
    }
}
