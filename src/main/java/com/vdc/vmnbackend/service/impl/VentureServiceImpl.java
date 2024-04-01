package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dao.repository.VentureRepository;
import com.vdc.vmnbackend.dto.req.InviteVentureReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.exception.ApiRuntimeException;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.VentureService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VentureServiceImpl implements VentureService {

    private final VentureRepository ventureRepository;
    private final UserService userService;

    public VentureServiceImpl(VentureRepository ventureRepository, UserService userService) {
        this.ventureRepository = ventureRepository;
        this.userService = userService;
    }

    public BasicResDTO createVenture(InviteVentureReqDTO inviteVentureReqDTO, UserDAO userDAO) {
        VentureDAO ventureDAO = new VentureDAO();
        ventureDAO.setName(inviteVentureReqDTO.ventureName());
        ventureDAO.setCampus(userService.getCampus(userDAO));
        ventureDAO.setStage(inviteVentureReqDTO.ventureStage());
        ventureDAO.setCoachId(userDAO);
        ventureDAO.setTag(ventureTagGenerator(userService.getCampus(userDAO)));
        ventureRepository.save(ventureDAO);
        return new BasicResDTO("Venture created successfully", HttpStatus.OK);
    }

    public ResponseDTO<List<VentureDAO>> getAllVentures(UserDAO userDAO) {
        return new ResponseDTO<List<VentureDAO>>( ventureRepository.findAllByCoachId(userDAO), new BasicResDTO("Ventures fetched successfully", HttpStatus.OK));
    }

    public Optional<VentureDAO> getVentureById(UUID ventureId) {
        return ventureRepository.findById(ventureId);
    }

    String ventureTagGenerator(Campus campus) {
        long cnt = ventureRepository.findAll().stream().filter(ventureDAO -> ventureDAO.getCampus() == campus).count() + 1;
        String cmp = (campus == Campus.Bengaluru || campus == Campus.Vishakhapatnam) ? ((campus == Campus.Bengaluru) ? "BLR":"HYD") : "VZG";
        return (cmp + String.format("%04d", cnt));
    }
}
