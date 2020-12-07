package com.example.memegenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memegenerator.request.UserDetailsRequestModel;
import com.example.memegenerator.response.UserRest;
import com.example.memegenerator.shared.dto.UserDto;
import com.example.memegenerator.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(path = "/")
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
	{	
		UserDto userDto = new UserDto();
		userDto.username = userDetails.username;
		userDto.password = userDetails.password;
		userDto.email = userDetails.email;
		userDto.activated = 0;
		
		// Create user
		UserDto createdUser = userService.createUser(userDto);
		
		// Show newly created user to the client
		UserRest newUser = new UserRest();
		newUser.username = createdUser.username;
		newUser.email = createdUser.email;
		
		return newUser;
	}
}
