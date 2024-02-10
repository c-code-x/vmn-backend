package com.vdc.vmnbackend.enumerators;

/**
 * Enumerates the roles that users can have in the system.
 */
public enum Roles {
    /**
     * Represents a super admin role with maximum privileges.
     */
    SUPER_ADMIN,

    /**
     * Represents an admin role with administrative privileges.
     */
    ADMIN,

    /**
     * Represents a mentor role providing guidance and expertise.
     */
    MENTOR,

    /**
     * Represents a coach role responsible for coaching and mentoring.
     */
    COACH,

    /**
     * Represents a mentee role for individuals seeking guidance and support.
     */
    MENTEE,

    /**
     * Represents a general user role with standard privileges.
     */
    USER
}
