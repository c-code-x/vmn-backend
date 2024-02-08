package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dto.req.InviteVentureReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.VentureService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("coach")
public class CoachController {
    private final VentureService ventureService;
    private final UserService userService;
    public CoachController(VentureService ventureService, UserService userService) {
        this.ventureService = ventureService;
        this.userService = userService;
    }

    @PostMapping("venture")
    BasicResDTO createVenture(Authentication authentication, @RequestBody @Valid InviteVentureReqDTO inviteVentureReqDTO){
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return ventureService.createVenture(inviteVentureReqDTO, userDAO);
    }
    @GetMapping("venture")
    ResponseDTO<List<VentureDAO>> getAllVentures(Authentication authentication){
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return ventureService.getAllVentures(userDAO);
    }
}
