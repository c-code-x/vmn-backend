package com.vdc.vmnbackend.dao.repository;

import com.vdc.vmnbackend.dao.InvitationDAO;
import com.vdc.vmnbackend.dto.req.UserInviteReqDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<InvitationDAO, UUID> {

    Optional<InvitationDAO> findByReceiverMailId(String emailId);
    Optional<InvitationDAO> findByInvId(UUID invId);

    boolean existsByReceiverMailId(String emailId);

    @Override
    List<InvitationDAO> findAll();
}
