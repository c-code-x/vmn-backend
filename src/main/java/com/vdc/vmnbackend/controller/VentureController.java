package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ProfileDataResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for managing venture-related operations.
 */
@RestController("venture")
public class VentureController {

    /**
     * Constructor for VentureController class.
     *
     * @param ventureService The VentureService for managing venture-related operations.
     */
    public VentureController(VentureService ventureService) {
    }
}
