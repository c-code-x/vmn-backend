package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
private final UserRepository userRepository;

public UserDetailsServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;

}
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        var optionalUserEntity = userRepository.findByEmailId(emailId);
        if (optionalUserEntity.isEmpty())
            throw new UsernameNotFoundException(emailId);
        UserDAO userDAO = optionalUserEntity.get();
        return new User(userDAO.getEmailId(), userDAO.getPassword(), List.of(new SimpleGrantedAuthority(userDAO.getRole().name())));
    }
}
