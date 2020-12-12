package com.example.memegenerator.config;

import javax.ws.rs.HttpMethod;

import com.example.memegenerator.security.Role;
import com.example.memegenerator.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String HOME_PATH = "/";
    private static final String USER_PATH = "/users";
    private static final String PASSWORD_RESET_REQUEST_PATH = "/users/password-reset-request";
    private static final String PASSWORD_RESET_PATH = "/users/password-reset";
    private static final String MEME_PATH = "/meme";

    @Autowired
    UserService userDetailService;

    @Bean
    protected AuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailService);
        return authProvider;
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        assert auth != null;
        auth.authenticationProvider(authenticationProvider(passwordEncoder()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .cors()
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, HOME_PATH).permitAll()
                .antMatchers(HttpMethod.GET, USER_PATH).hasRole(Role.Admin.toString())
                .antMatchers(HttpMethod.POST, USER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_REQUEST_PATH).permitAll()
                .antMatchers(HttpMethod.POST, PASSWORD_RESET_PATH).permitAll()
                .antMatchers(MEME_PATH).permitAll()
                .anyRequest().authenticated()
            .and()
                .httpBasic()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}