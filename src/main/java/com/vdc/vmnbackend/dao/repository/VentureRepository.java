package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VentureRepository extends JpaRepository<VentureDAO, UUID> {
    List<VentureDAO> findAllByCoachId(UserDAO userDAO);
}
