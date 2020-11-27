package com.example.assignment2.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.assignment2.domain.*;
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
		
		com.example.assignment2.domain.User checkIfEmailExists = userRepository.findByEmail(user.email);
		
		if(checkIfEmailExists != null) throw new RuntimeException("A user with this email already exists.");
		
		User userEntity = new User(null, null, false, false, false, false, null);
		BeanUtils.copyProperties(user, userEntity);
		
		//userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		//userEntity.setUserId("test12345");
		
		//User storedUserDetails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		//BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
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
