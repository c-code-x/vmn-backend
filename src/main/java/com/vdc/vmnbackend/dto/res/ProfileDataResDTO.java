package com.vdc.vmnbackend.dto.res;

import com.vdc.vmnbackend.dao.UserDAO;

/**
 * Represents a response DTO containing profile data, including user information and role-specific data.
 *
 * @param <D> The type of role-specific data associated with the profile.
 */
public record ProfileDataResDTO<D>(
        /**
         * The user data associated with the profile.
         */
        UserDAO userDAO,

        /**
         * The role-specific data associated with the profile.
         */
        D roleSpecificData
) {
}
