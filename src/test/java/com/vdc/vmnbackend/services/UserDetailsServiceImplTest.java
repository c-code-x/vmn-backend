package com.vdc.vmnbackend.services;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.UserRepository;
import com.vdc.vmnbackend.enumerators.Roles;
import com.vdc.vmnbackend.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        UserDAO userDAO = new UserDAO();
        userDAO.setEmailId("test@example.com");
        userDAO.setPassword("password");
        userDAO.setRole(Roles.USER);
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(userDAO));

        UserDetails result = userDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
        assertEquals("password", result.getPassword());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("test@example.com"));
    }
}