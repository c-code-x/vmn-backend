package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dto.req.InviteVentureReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VentureService {
    public BasicResDTO createVenture(InviteVentureReqDTO inviteVentureReqDTO, UserDAO userDAO);

    ResponseDTO<List<VentureDAO>> getAllVentures(UserDAO userDAO);

    Optional<VentureDAO> getVentureById(UUID ventureId);
}
