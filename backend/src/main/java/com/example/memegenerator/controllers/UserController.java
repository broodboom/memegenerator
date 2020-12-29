package com.example.memegenerator.controllers;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.memegenerator.domain.User;
import com.example.memegenerator.repository.UserRepository;
import com.example.memegenerator.request.PasswordResetModel;
import com.example.memegenerator.request.PasswordResetRequestModel;
import com.example.memegenerator.request.UserDetailsRequestModel;
import com.example.memegenerator.response.UserRest;
import com.example.memegenerator.shared.dto.UserDto;
import com.example.memegenerator.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;

	// @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	// public List<UserRest> getUsers() {
	// 	List<UserDto> userDtos = userService.getUsers();

	// 	Type targetType = new TypeToken<List<UserRest>>() {
	// 	}.getType();

	// 	return new ModelMapper().map(userDtos, targetType);
	// }

	// @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	// public UserRest getUser(@PathVariable long id) {
	// 	UserDto userDto = userService.getUserByUserId(id);

	// 	return new ModelMapper().map(userDto, UserRest.class);
	// }

	// @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// public UserDto createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

	// 	UserDto userDto = new UserDto();

	// 	userDto.username = userDetails.username;
	// 	userDto.email = userDetails.email;
	// 	userDto.password = userDetails.password;

	// 	BeanUtils.copyProperties(userDetails, userDto);

	// 	userService.createUser(userDto);

	// 	return userDto;
	// }

	// @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// public UserRest updateUser(@PathVariable long id, @RequestBody UserDetailsRequestModel userDetails) {
	// 	UserDto userDto = new UserDto();
	// 	userDto = new ModelMapper().map(userDetails, UserDto.class);

	// 	UserDto updateUser = userService.updateUser(id, userDto);

	// 	return new ModelMapper().map(updateUser, UserRest.class);
	// }

	// @PostMapping(path = "/password-reset-request", consumes = MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<?> requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
	// 	userService.requestPasswordReset(passwordResetRequestModel.email);

	// 	return new ResponseEntity<String>("", HttpStatus.OK);
	// }

	// @PostMapping(path = "/password-reset", consumes = MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<?> resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
	// 	userService.resetPassword(passwordResetModel.token, passwordResetModel.password);

	// 	return new ResponseEntity<String>("", HttpStatus.OK);
	// }

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

		//return ResponseEntity.ok("Id: " + id + ", token " + token);

		return userService.activateUser(id, token);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<User> getUserInfo(@PathVariable long id){
		return userService.getUserByIdResponseEntity(id);
	}
}