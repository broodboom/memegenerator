package com.example.memegenerator.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.memegenerator.web.dto.UserDto;

import java.util.NoSuchElementException;

import javax.ejb.DuplicateKeyException;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto) throws NoSuchElementException;

    UserDto updateUser(UserDto userDto) throws NoSuchElementException, DuplicateKeyException;

    UserDto getUserById(long userId) throws NoSuchElementException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void requestPasswordReset(String email) throws NoSuchElementException;
    
    void resetPassword(String confirmationToken, String password) throws NoSuchElementException;

    void activateUser(Long userId, String confirmationToken) throws NoSuchElementException;
}