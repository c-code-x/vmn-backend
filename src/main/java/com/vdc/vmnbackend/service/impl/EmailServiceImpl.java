package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.EmailService;
import com.vdc.vmnbackend.utility.CommonConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
        private final JavaMailSender javaMailSender;

        private final String senderEmailId;

        private final Configuration config;


    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String senderEmailId, Configuration config) {
        this.javaMailSender = javaMailSender;
        this.senderEmailId = senderEmailId;
        this.config = config;
    }

    public BasicResDTO sendEmail(String toEmailId, String subject, String body) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(senderEmailId);
                message.setTo(toEmailId);
                message.setSubject(subject);
                message.setText(body);
                javaMailSender.send(message);
                return new BasicResDTO(CommonConstants.MAIL_SUCCESS, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiRuntimeException(CommonConstants.MAIL_ERROR, HttpStatus.BAD_REQUEST);
            }
    }
    public BasicResDTO sendInvitationEmail(InvitationDAO invitationDAO, String senderName) {
//        MailResponse response = new MailResponse();
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
//            // add attachment
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template t = config.getTemplate("InvitationMailTemplates.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, Map.of("name", invitationDAO.getName(),"role",invitationDAO.getToRole(), "senderName", senderName, "token", invitationDAO.getInvId()));

            helper.setTo(invitationDAO.getReceiverMailId());
            helper.setText(html, true);
            helper.setSubject("<Test>Invitation to join VMN");
            javaMailSender.send(message);
            return new BasicResDTO(CommonConstants.MAIL_SUCCESS, HttpStatus.OK);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new ApiRuntimeException(CommonConstants.MAIL_ERROR, HttpStatus.BAD_REQUEST);
        }

    }

}
