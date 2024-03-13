package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.InvitationStatus;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.enumerators.VentureStage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class InvitationRepositoryTest {

    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VentureRepository ventureRepository;

    @Test
    public void testSaveAndFindByReceiverMailId() {

        UserDAO sender = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Sender User")
                .emailId("sender@gmail.com")
                .role(Roles.USER)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer")
                .build();


        userRepository.save(sender);
        VentureDAO venture = VentureDAO.builder()
                .vid(UUID.randomUUID())
                .coachId(sender)
                .name("Venture Name")
                .campus(Campus.Bengaluru)
                .tag("Venture Tag")
                .stage(VentureStage.GO)
                .serviceArea("Service Area")
                .bio("Venture Bio")
                .sector("Venture Sector")
                .build();

        ventureRepository.save(venture);

        InvitationDAO invitation = InvitationDAO.builder()
                .sender(sender)
                .receiverMailId("receiver@gmail.com")
                .name("Receiver Name")
                .toRole(Roles.ADMIN)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .venture(venture)
                .build();


        invitationRepository.save(invitation);


        Optional<InvitationDAO> foundInvitation = invitationRepository.findByReceiverMailId(invitation.getReceiverMailId());


        Assertions.assertTrue(foundInvitation.isPresent());


        Assertions.assertEquals("Receiver Name", foundInvitation.get().getName());
    }

    @Test
    public void testFindByInvId() {

        UserDAO sender = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Sender User")
                .emailId("sender@gmail.com")
                .role(Roles.USER)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer")
                .build();


        userRepository.save(sender);

        VentureDAO venture = VentureDAO.builder()
                .vid(UUID.randomUUID())
                .coachId(sender)
                .name("Venture Name")
                .campus(Campus.Bengaluru)
                .tag("Venture Tag")
                .stage(VentureStage.GO)
                .serviceArea("Service Area")
                .bio("Venture Bio")
                .sector("Venture Sector")
                .build();

        ventureRepository.save(venture);

        InvitationDAO invitation = InvitationDAO.builder()
                .invId(UUID.randomUUID())
                .sender(sender)
                .receiverMailId("receiver@gmail.com")
                .name("Receiver Name")
                .toRole(Roles.ADMIN)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();


        invitationRepository.save(invitation);


        Optional<InvitationDAO> foundInvitation = invitationRepository.findByInvId(invitation.getInvId());


        Assertions.assertTrue(foundInvitation.isPresent());


        Assertions.assertEquals("Receiver Name", foundInvitation.get().getName());
    }

    @Test
    public void testExistsByReceiverMailId() {
        UserDAO sender = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Sender User")
                .emailId("sender@gmail.com")
                .role(Roles.USER)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer")
                .build();

        userRepository.save(sender);

        VentureDAO venture = VentureDAO.builder()
                .vid(UUID.randomUUID())
                .coachId(sender)
                .name("Venture Name")
                .campus(Campus.Bengaluru)
                .tag("Venture Tag")
                .stage(VentureStage.GO)
                .serviceArea("Service Area")
                .bio("Venture Bio")
                .sector("Venture Sector")
                .build();

        ventureRepository.save(venture);

        InvitationDAO invitation = InvitationDAO.builder()
                .invId(UUID.randomUUID())
                .sender(sender)
                .receiverMailId("receiver@gmail.com")
                .name("Receiver Name")
                .toRole(Roles.ADMIN)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        invitationRepository.save(invitation);

        boolean exists = invitationRepository.existsByReceiverMailId(invitation.getReceiverMailId());

        Assertions.assertTrue(exists);
    }
}
