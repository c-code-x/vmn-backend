package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.CoachDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CoachRepositoryTest {
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void CoachRepository_SaveAndFindByCoachId_ReturnCoach() {
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

        CoachDAO coachDAO = CoachDAO.builder()
                .coachId(savedUserDAO)
                .adminId(savedUserDAO)
                .campus(Campus.Bengaluru)
                .bio("I am a coach, This is My Bio!").build();

        CoachDAO savedCoachDAO = coachRepository.save(coachDAO);
        CoachDAO foundCoachDAO = coachRepository.findByCoachId(savedUserDAO);

        Assertions.assertNotNull(foundCoachDAO);
        Assertions.assertEquals(savedCoachDAO.getCoachId(), foundCoachDAO.getCoachId());

    }

}
