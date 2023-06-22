package com.group13.nyseenowbackend.config;

import com.alibaba.fastjson.JSONObject;
import com.group13.nyseenowbackend.entity.RestBean;
import com.group13.nyseenowbackend.service.impl.AuthorizeServiceimpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    AuthorizeServiceimpl authorizeService;

    @Resource
    DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PersistentTokenRepository repository) throws Exception {
        return http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler(this::authenticationSuccessHandler)
                        .failureHandler(this::authenticationFailureHandler))
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout"))
                .csrf(csrf -> csrf
                        .disable())
                .rememberMe(rememberMe -> rememberMe
                        .tokenValiditySeconds(3600 * 24 * 7) // 7 days
                        .rememberMeParameter("remember")
                        .tokenRepository(repository))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(this::authenticationFailureHandler))
                .build();
    }

    /*use jdbc to store remember me token*/
    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Bean
    public DaoAuthenticationConfigurer<AuthenticationManagerBuilder, AuthorizeServiceimpl> authenticationManager(HttpSecurity security) throws Exception {
        return security
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(authorizeService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void authenticationFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding("utf-8");
        if (exception instanceof BadCredentialsException) {
            response.getWriter().write(JSONObject.toJSONString(RestBean.failure(401, "Invalid username or password.")));
        } else {
            response.getWriter().write(JSONObject.toJSONString(RestBean.failure(401, exception.getMessage())));
        }
    }


    public void authenticationSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.success("LOGGED IN!")));
    }

}
