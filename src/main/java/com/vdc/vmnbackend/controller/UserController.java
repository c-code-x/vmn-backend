package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ProfileDataResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseDTO<ProfileDataResDTO<?>> getProfile(Authentication authentication) {
        ProfileDataResDTO<?> payload = userService.getProfile(userService.getByEmail(authentication.getName()));
        return new ResponseDTO<>(payload, new BasicResDTO(CommonConstants.PROFILE_FETCHED, HttpStatus.OK));
    }
    @PutMapping
    public BasicResDTO updateProfile(Authentication authentication, @RequestBody ProfileDataResDTO<?> profileDataResDTO) {
        return userService.updateProfile(userService.getByEmail(authentication.getName()), profileDataResDTO);
    }
}
