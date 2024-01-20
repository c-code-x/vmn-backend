package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.MentorDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MentorRepository extends JpaRepository<MentorDAO, UUID> {
}
