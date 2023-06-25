package com.group13.nyseenowbackend.service;

import com.group13.nyseenowbackend.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    String validateAndRegister(String username, String password, String email);
}
