package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;

import java.util.Map;

public interface EmailService {
    public BasicResDTO sendEmail(String toEmailId, String subject, String body);
    public BasicResDTO sendInvitationEmail(InvitationDAO invitationDAO, String senderName);
}
