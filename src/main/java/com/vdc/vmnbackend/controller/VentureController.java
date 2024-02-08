package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.service.VentureService;
import org.springframework.web.bind.annotation.RestController;

@RestController("venture")
public class VentureController {

    public VentureController(VentureService ventureService) {
    }
}
