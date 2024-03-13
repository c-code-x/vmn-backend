package com.vdc.vmnbackend.services;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dao.repository.VentureRepository;
import com.vdc.vmnbackend.dto.req.InviteVentureReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.VentureStage;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.impl.VentureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VentureServiceImplTest {

    @Mock
    private VentureRepository ventureRepository;

    @Mock
    private UserService userService;

    private VentureServiceImpl ventureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ventureService = new VentureServiceImpl(ventureRepository, userService);
    }

    @Test
    public void testCreateVenture() {
        UserDAO userDAO = new UserDAO();
        InviteVentureReqDTO inviteVentureReqDTO = new InviteVentureReqDTO("Test Venture", VentureStage.GO);

        when(userService.getCampus(any(UserDAO.class))).thenReturn(Campus.Bengaluru);
        when(ventureRepository.save(any(VentureDAO.class))).thenReturn(new VentureDAO());

        BasicResDTO result = ventureService.createVenture(inviteVentureReqDTO, userDAO);

        assertEquals("Venture created successfully", result.message());
    }


}