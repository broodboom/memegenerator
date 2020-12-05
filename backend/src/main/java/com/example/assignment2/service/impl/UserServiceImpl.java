package com.example.assignment2.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.assignment2.domain.Role;
import com.example.assignment2.domain.UserEntity;
import com.example.assignment2.respository.UserRepository;
import com.example.assignment2.service.UserService;
import com.example.assignment2.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	//@Override
	public UserDto createUser(UserDto user) {
		UserEntity checkIfEmailExists = userRepository.findByEmail(user.email);
		UserEntity checkIfUsernameExists = userRepository.findByUsername(user.username);
		
		// Check if email is already in use
		// TODO: More graceful error messages
		if(checkIfEmailExists != null) throw new RuntimeException("A user with this email already exists.");
		
		// Check if username is already in use
		if(checkIfUsernameExists != null) throw new RuntimeException("A user with this username already exists.");
		
		// Set user entity properties
		UserEntity userEntity = new UserEntity();
		userEntity.username = user.username;
		
		// Encrypt password
		userEntity.password = bCryptPasswordEncoder.encode(user.password);
		
		//TODO: Validate user email
		userEntity.email = user.email;
		
		userEntity.activated = 0;
		userEntity.role = Role.User;
		
		// The created user
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		// Create new object to show newly created user's username
		UserDto createdUserDto = new UserDto();
		createdUserDto.username = storedUserDetails.username;
		
		return createdUserDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.username, userEntity.password, new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyEmailToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requestPasswordReset(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetPassword(String token, String password) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		UserEntity userEntity = userRepository.findByEmail(email);
//		
//		if(userEntity == null) throw new UsernameNotFoundException(email);
//		
//		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
//	}
}
