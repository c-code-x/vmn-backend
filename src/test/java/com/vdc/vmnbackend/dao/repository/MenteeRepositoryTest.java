package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.MenteeDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.enumerators.VentureStage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MenteeRepositoryTest {
    @Autowired
    private MenteeRepository menteeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VentureRepository ventureRepository;

    @Test
    public void findByMenteeId_ReturnMentee(){
        UserDAO userDAO = UserDAO.builder()
                .uid(UUID.randomUUID())
                .name("Test User")
                .emailId("abc@gmail.com")
                .role(Roles.ADMIN)
                .password("password")
                .contact("1234567890")
                .linkedIn("https://www.linkedin.com/in/abc")
                .designation("Software Engineer").build();


        UserDAO savedUserDAO = userRepository.save(userDAO);

        VentureDAO venture = VentureDAO.builder()
                .vid(UUID.randomUUID())
                .coachId(savedUserDAO)
                .name("Venture Name")
                .campus(Campus.Bengaluru)
                .tag("Venture Tag")
                .stage(VentureStage.GO)
                .serviceArea("Service Area")
                .bio("Venture Bio")
                .sector("Venture Sector")
                .build();

        VentureDAO savedVenture = ventureRepository.save(venture);


        MenteeDAO menteeDAO = MenteeDAO.builder()
                .menteeId(savedUserDAO)
                .campus(Campus.Bengaluru)
                .ventureId(savedVenture)
                .build();

        MenteeDAO savedMentee = menteeRepository.save(menteeDAO);

        MenteeDAO foundMentee = menteeRepository.findMenteeDAOByMenteeId(savedUserDAO);

        Assertions.assertNotNull(foundMentee);
        Assertions.assertEquals(savedMentee.getMenteeId(), foundMentee.getMenteeId());
    }
}
