package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.AdminDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void AdminRepository_SaveAndFindByAdminId_ReturnAdmin() {
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



        AdminDAO adminDAO = AdminDAO.builder()
                .adminId(savedUserDAO)
                .createdBy(savedUserDAO)
                .campus(Campus.Bengaluru)
                .bio("I am an admin, This is My Bio!").build();


        AdminDAO savedAdminDAO = adminRepository.save(adminDAO);


        AdminDAO foundAdminDAO = adminRepository.findByAdminId(savedUserDAO);

        Assertions.assertNotNull(foundAdminDAO);
        Assertions.assertEquals(savedAdminDAO.getAdminId(), foundAdminDAO.getAdminId());
    }

}