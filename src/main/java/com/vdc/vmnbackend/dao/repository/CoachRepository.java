package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.CoachDAO;
import com.vdc.vmnbackend.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoachRepository extends JpaRepository<CoachDAO, UUID>  {
    CoachDAO findByCoachId(UserDAO userDAO);
}
