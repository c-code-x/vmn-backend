package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.MenteeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenteeRepository extends JpaRepository<MenteeDAO, UUID> {

}
