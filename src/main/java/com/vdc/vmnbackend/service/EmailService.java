package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dto.res.BasicResDTO;

public interface EmailService {
    public BasicResDTO sendEmail(String toEmailId, String subject, String body);
}
