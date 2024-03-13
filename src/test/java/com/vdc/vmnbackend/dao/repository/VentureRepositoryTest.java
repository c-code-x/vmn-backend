package com.vdc.vmnbackend.dao.repository;

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

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class VentureRepositoryTest {
    @Autowired
    private VentureRepository ventureRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllByCoachId(){

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

        List<VentureDAO> foundVenture = ventureRepository.findAllByCoachId(savedUserDAO);

        Assertions.assertNotNull(foundVenture);
        Assertions.assertEquals(savedVenture.getVid(), foundVenture.get(0).getVid());
    }

}
