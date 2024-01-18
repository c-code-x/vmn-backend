package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDAO, UUID> {

    Optional<UserDAO> findByEmailId(String emailId);

    Boolean existsByEmailId(String emailId);
}
