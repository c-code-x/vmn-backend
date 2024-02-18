package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dto.req.InviteBasedUserReqDTO;
import com.vdc.vmnbackend.dto.req.UserSigninReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;
import com.vdc.vmnbackend.dto.res.TokenResDTO;
import com.vdc.vmnbackend.service.InvitationService;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.impl.TokenServiceImpl;
import com.vdc.vmnbackend.utility.CommonConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller class for handling authentication-related operations.
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenServiceImpl tokenService;

    @Autowired
    private Environment environment;

    /**
     * Constructor for AuthController class.
     *
     * @param authenticationManager The AuthenticationManager for authenticating users.
     * @param userService           The UserService for managing user-related operations.
     * @param tokenService          The TokenServiceImpl for generating authentication tokens.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, TokenServiceImpl tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * Endpoint for testing the authentication controller.
     *
     * @return A test message or property value retrieved from the environment.
     */
    @GetMapping("test")
    public String test() {
        return environment.getProperty("MAIL_PASSWORD");
    }

    /**
     * Endpoint for user login.
     *
     * @param userSigninReqDTO The UserSigninReqDTO containing user credentials.
     * @return A ResponseDTO containing authentication token and status.
     */
    @PostMapping("login")
    ResponseDTO<TokenResDTO> login(@RequestBody UserSigninReqDTO userSigninReqDTO) {
        var credentials = new UsernamePasswordAuthenticationToken(userSigninReqDTO.emailId(),
                userSigninReqDTO.password());
        var authentication = authenticationManager.authenticate(credentials);
        var payload = tokenService.generateToken(authentication);
        return new ResponseDTO<TokenResDTO>(payload, new BasicResDTO(CommonConstants.LOGIN_SUCCESSFUL, HttpStatus.OK));
    }

    /**
     * Endpoint for token renewal (refresh).
     *
     * @param authentication The current authentication token.
     * @return A ResponseDTO containing the renewed authentication token and status.
     */
    @GetMapping("renew-token")
    public ResponseDTO<TokenResDTO> refresh(Authentication authentication) {
        var payload = tokenService.generateToken(authentication);
        return new ResponseDTO<TokenResDTO>(payload, new BasicResDTO(CommonConstants.TOKEN_RENEWED, HttpStatus.OK));
    }
}
