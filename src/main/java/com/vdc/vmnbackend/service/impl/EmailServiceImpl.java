package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
        private final JavaMailSender javaMailSender;

        private final String senderEmailId;


    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String senderEmailId) {
        this.javaMailSender = javaMailSender;
        this.senderEmailId = senderEmailId;
    }

    public BasicResDTO sendEmail(String toEmailId, String subject, String body) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(senderEmailId);
                message.setTo(toEmailId);
                message.setSubject(subject);
                message.setText(body);
                javaMailSender.send(message);
                return new BasicResDTO("Mail sent Successfully!!", HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiRuntimeException("Error while sending email", HttpStatus.BAD_REQUEST);
            }
    }
}
