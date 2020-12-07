package com.example.assignment2.controllers;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment2.request.PasswordResetModel;
import com.example.assignment2.request.PasswordResetRequestModel;
import com.example.assignment2.request.UserDetailsRequestModel;
import com.example.assignment2.response.UserRest;
import com.example.assignment2.shared.dto.UserDto;
import com.example.assignment2.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping()
	public List<UserRest> getUsers() {
		List<UserDto> userDtos = userService.getUsers();

		Type targetType = new TypeToken<List<UserRest>>() {
		}.getType();

		return new ModelMapper().map(userDtos, targetType);
	}

	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable Integer id) {
		UserDto userDto = userService.getUserByUserId(id);

		return new ModelMapper().map(userDto, UserRest.class);
	}

	@PostMapping()
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);

		return modelMapper.map(createdUser, UserRest.class);
	}

	@PutMapping(path = "/{id}")
	public UserRest updateUser(@PathVariable Integer id, @RequestBody UserDetailsRequestModel userDetails) {
		UserDto userDto = new UserDto();
		userDto = new ModelMapper().map(userDetails, UserDto.class);

		UserDto updateUser = userService.updateUser(id, userDto);

		return new ModelMapper().map(updateUser, UserRest.class);
	}

	@PostMapping(path = "/password-reset-request")
	public ResponseEntity<?> requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
		userService.requestPasswordReset(passwordResetRequestModel.getEmail());

		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@PostMapping(path = "/password-reset")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
		userService.resetPassword(passwordResetModel.getToken(), passwordResetModel.getPassword());

		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
