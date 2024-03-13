package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.enumerators.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailId_returnUser()
    {
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

        Optional<UserDAO> foundUserDAO = userRepository.findByEmailId(savedUserDAO.getEmailId());
        boolean exists = userRepository.existsByEmailId(savedUserDAO.getEmailId());
    }
}
