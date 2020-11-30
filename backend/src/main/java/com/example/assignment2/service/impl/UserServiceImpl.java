package com.example.assignment2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		// TODO: Encrypt password
		userEntity.password = user.password;
		
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
		return null;
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
