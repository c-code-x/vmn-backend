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
/**
 * Controller class for managing coach-related operations.
 */
@RestController("coach")
public class CoachController {

    private final VentureService ventureService;
    private final UserService userService;

    /**
     * Constructor for CoachController class.
     *
     * @param ventureService The VentureService for managing venture-related operations.
     * @param userService    The UserService for managing user-related operations.
     */
    public CoachController(VentureService ventureService, UserService userService) {
        this.ventureService = ventureService;
        this.userService = userService;
    }

    /**
     * Endpoint for creating a new venture.
     *
     * @param authentication      The authentication object representing the logged-in user.
     * @param inviteVentureReqDTO The InviteVentureReqDTO containing details of the venture to be created.
     * @return A BasicResDTO indicating the result of the venture creation operation.
     */
    @PostMapping("venture")
    BasicResDTO createVenture(Authentication authentication, @RequestBody @Valid InviteVentureReqDTO inviteVentureReqDTO) {
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return ventureService.createVenture(inviteVentureReqDTO, userDAO);
    }

    /**
     * Endpoint for retrieving all ventures associated with the logged-in coach.
     *
     * @param authentication The authentication object representing the logged-in user.
     * @return A ResponseDTO containing a list of VentureDAO objects representing all ventures associated with the coach.
     */
    @GetMapping("venture")
    ResponseDTO<List<VentureDAO>> getAllVentures(Authentication authentication) {
        UserDAO userDAO = userService.getByEmail(authentication.getName());
        return ventureService.getAllVentures(userDAO);
    }
}
