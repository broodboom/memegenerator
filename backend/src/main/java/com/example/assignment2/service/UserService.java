package com.example.assignment2.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.assignment2.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(Integer userId);
	UserDto updateUser(Integer userId, UserDto user);
	List<UserDto> getUsers();
	void requestPasswordReset(String email);
	boolean resetPassword(String token, String password);
}
   