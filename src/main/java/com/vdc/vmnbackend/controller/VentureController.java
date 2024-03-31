package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.service.VentureService;
import org.springframework.web.bind.annotation.RestController;
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
