package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.AdminDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminDAO, UUID> {

    AdminDAO findByAdminId(UserDAO userDAO);
}
