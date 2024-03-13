package com.vdc.vmnbackend.services;

import com.vdc.vmnbackend.config.HTMLEmailConfig;
import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.EmailService;
import com.vdc.vmnbackend.service.impl.EmailServiceImpl;
import com.vdc.vmnbackend.utility.CommonConstants;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private HTMLEmailConfig configuration;


    @InjectMocks
    private EmailServiceImpl emailService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_Success() {
        // Arrange
        String toEmail = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";


        // Act
        BasicResDTO response = emailService.sendEmail(toEmail, subject, body);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.status().value());
        assertEquals(CommonConstants.MAIL_SUCCESS, response.message());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_Failure() {
        // Arrange
        String toEmail = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        doThrow(new RuntimeException(CommonConstants.MAIL_ERROR)).when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertThrows(ApiRuntimeException.class, () -> emailService.sendEmail(toEmail, subject, body));
    }




}
