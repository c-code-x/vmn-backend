package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.InvitationDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvitationRepository extends JpaRepository<InvitationDAO, UUID> {

}
