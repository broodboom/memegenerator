package com.example.assignment2.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.assignment2.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto user);
}
