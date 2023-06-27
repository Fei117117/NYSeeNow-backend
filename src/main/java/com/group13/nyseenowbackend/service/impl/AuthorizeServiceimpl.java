package com.group13.nyseenowbackend.service.impl;

import com.group13.nyseenowbackend.entity.Account;
import com.group13.nyseenowbackend.mapper.UserMapper;
import com.group13.nyseenowbackend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeServiceimpl implements AuthorizeService {

    @Resource
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new BadCredentialsException("Username cannot be empty.");
        }

        Account account = mapper.findAccountByNameOrEmail(username);
        if (account == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

}