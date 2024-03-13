package com.vdc.vmnbackend.controller;

import com.vdc.vmnbackend.dto.req.UserSigninReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.TokenResDTO;
import com.vdc.vmnbackend.service.UserService;
import com.vdc.vmnbackend.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private TokenServiceImpl tokenService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        AuthController authController = new AuthController(authenticationManager, userService, tokenService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLogin() throws Exception {
        UserSigninReqDTO userSigninReqDTO = new UserSigninReqDTO("test@example.com", "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSigninReqDTO.emailId(), userSigninReqDTO.password());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenService.generateToken(any())).thenReturn(new TokenResDTO("token", "Bearer"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"emailId\":\"test@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRefresh() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");
        when(tokenService.generateToken(any())).thenReturn(new TokenResDTO("token", "Bearer"));

        mockMvc.perform(get("/auth/renew-token")
                        .principal(authentication))
                .andExpect(status().isOk());
    }
}