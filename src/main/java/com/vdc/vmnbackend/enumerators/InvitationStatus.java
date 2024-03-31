package com.vdc.vmnbackend.enumerators;

/**
 * Enumerates the status of invitations.
 */
public enum InvitationStatus {
    /**
     * Represents an invitation that has been accepted.
     */
    ACCEPTED,

    /**
     * Represents an invitation that is pending and has not been accepted yet.
     */
    PENDING,

    /**
     * Represents an invitation that has expired.
     */
    EXPIRED
}
