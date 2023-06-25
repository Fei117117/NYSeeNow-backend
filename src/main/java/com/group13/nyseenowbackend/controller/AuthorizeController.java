package com.group13.nyseenowbackend.controller;

import com.group13.nyseenowbackend.entity.RestBean;
import com.group13.nyseenowbackend.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Resource
    AuthorizeService service;

    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    private final String NAME_REGEX ="^[a-zA-Z0-9_-]{3,16}$";
    @PostMapping("/register")
    public RestBean<String> registerUser(@RequestParam("username") @Length(min = 2, max = 8)@Pattern(regexp = NAME_REGEX) String username,
                                         @RequestParam("password") @Length(min = 6, max = 16) String password,
                                         @RequestParam("email") @NotEmpty(message = "Email is required") @Pattern(regexp = EMAIL_REGEX, message = "Invalid email format") String email){
        String s = service.validateAndRegister(username, password, email);
        if(s == null)
            return RestBean.success("Register Successfully");
            else
                return RestBean.failure(400, "Register Failed");
    }

}
