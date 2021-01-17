package com.example.memegenerator.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memegenerator.data.entity.User;
import com.example.memegenerator.web.dto.UserDto;
import com.example.memegenerator.domain.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping()
	public ResponseEntity<String> createUser(@Valid @RequestBody User user) {

		UserDto userDto = new UserDto();

		userDto.username = user.username;
		userDto.email = user.email;
		userDto.password = user.password;

		return userService.createUser(userDto);
	}

	@PutMapping()
	public ResponseEntity<String> updateUser(@Valid @RequestBody User user) {

		UserDto updateUserDto = new UserDto();

		updateUserDto.username = user.username;
		updateUserDto.email = user.email;
		updateUserDto.password = user.password;

		return userService.updateUser(user, updateUserDto);
	}

	@GetMapping(path = "/activate/{id}/{token}")
	public ResponseEntity<String> getUser(@PathVariable long id, @PathVariable int token) {
	
		return userService.activateUser(id, token);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<User> getUserInfo(@PathVariable long id) {

		return userService.getUserByIdResponseEntity(id);
	}
}