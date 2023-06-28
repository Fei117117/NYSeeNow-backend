package com.group13.nyseenowbackend.service.impl;

import com.group13.nyseenowbackend.model.Account;
import com.group13.nyseenowbackend.mapper.UserMapper;
import com.group13.nyseenowbackend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Resource
    UserMapper mapper;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new BadCredentialsException("Username cannot be empty.");
        }

        Account account = mapper.findAccountByName(username);
        if (account == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    };

    @Override
    public String validateAndRegister(String username, String password, String email){
        password = encoder.encode(password);

        // Check if the email already exists in the database
        Account existingAccount = mapper.findAccountByEmail(email);
        if (existingAccount != null) {
            return "Email already exists";
        }

        // Proceed with creating a new account
        if (mapper.creatAccount(username, password, email) > 0){
            return null; // Registration successful, no error message
        } else {
            return "Error creating account";
        } // Registration failed, return error message
    }

    @Override
    public String resetUser(String email, String newUsername, String newPassword){
        Account existingAccount = mapper.findAccountByEmail(email);
        if (existingAccount == null) {
            return "Email does not exist";
        }

        // Check if the new username already exists
        Account existingUsernameAccount = mapper.findAccountByName(newUsername);
        if (existingUsernameAccount != null) {
            return "New username already exists";
        }

        // If the email exists and the new username doesn't exist, proceed with the update
        existingAccount.setUsername(newUsername);
        existingAccount.setPassword(encoder.encode(newPassword));

        if (mapper.updateAccount(existingAccount) > 0){
            return null; // Reset successful, no error message
        } else {
            return "Error resetting account";
        } // Reset failed, return error message
    }

}
