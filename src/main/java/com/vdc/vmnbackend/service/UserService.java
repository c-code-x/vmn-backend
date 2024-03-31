package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dto.req.UserSignupReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ProfileDataResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.Campus;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Retrieves a user by their email ID.
     *
     * @param emailId The email ID of the user to retrieve.
     * @return The UserDAO associated with the email ID.
     */
    UserDAO getByEmail(String emailId);

    /**
     * Checks if a user exists based on their email ID.
     *
     * @param emailId The email ID to check.
     * @return True if the user exists, false otherwise.
     */
    Boolean existsByEmailId(String emailId);

    /**
     * Creates a new user.
     *
     * @param userSignupReqDTO The request containing details of the new user.
     * @param invitationDAO     The InvitationDAO associated with the user's invitation.
     */
    void createUser(UserSignupReqDTO userSignupReqDTO, InvitationDAO invitationDAO);

    /**
     * Retrieves the campus of a user.
     *
     * @param userDAO The UserDAO of the user.
     * @return The Campus associated with the user.
     */
    Campus getCampus(UserDAO userDAO);

    /**
     * Retrieves the profile data of a user.
     *
     * @param userDAO The UserDAO of the user.
     * @return The ProfileDataResDTO containing the user's profile data.
     */
    ProfileDataResDTO getProfile(UserDAO userDAO);

    /**
     * Updates the profile of a user.
     *
     * @param byEmail            The UserDAO of the user whose profile is being updated.
     * @param profileDataResDTO The new profile data.
     * @return A BasicResDTO indicating the result of the operation.
     */
    BasicResDTO updateProfile(UserDAO byEmail, ProfileDataResDTO<?> profileDataResDTO);
}
