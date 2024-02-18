package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;

/**
 * Defines methods for sending emails.
 */
public interface EmailService {

    /**
     * Sends an email to the specified recipient.
     * 
     * @param toEmailId The email address of the recipient.
     * @param subject   The subject of the email.
     * @param body      The body/content of the email.
     * @return A BasicResDTO indicating the result of the operation.
     */
    public BasicResDTO sendEmail(String toEmailId, String subject, String body);

    /**
     * Sends an invitation email using the details from the provided InvitationDAO.
     * 
     * @param invitationDAO The InvitationDAO containing the details of the invitation.
     * @param senderName    The name of the sender.
     * @return A BasicResDTO indicating the result of the operation.
     */
    public BasicResDTO sendInvitationEmail(InvitationDAO invitationDAO, String senderName);
}
