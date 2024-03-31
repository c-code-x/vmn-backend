package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.MentorDAO;
import com.vdc.vmnbackend.dao.UserDAO;
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
public class MentorRepositoryTest {
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByMentorId() {

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

        MentorDAO mentor = MentorDAO.builder()
                .mentorId(savedUserDAO)
                .adminId(savedUserDAO)
                .bio("I am a mentor, This is My Bio!")
                .expertise("Java")
                .experience("5 years").build();

        MentorDAO savedMentor = mentorRepository.save(mentor);

        MentorDAO foundMentor = mentorRepository.findByMentorId(savedUserDAO);

        Assertions.assertNotNull(foundMentor);
        Assertions.assertEquals(savedMentor.getMentorId(), foundMentor.getMentorId());
    }
}
