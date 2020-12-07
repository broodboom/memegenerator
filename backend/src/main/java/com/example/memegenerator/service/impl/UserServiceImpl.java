package com.example.memegenerator.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.memegenerator.domain.Role;
import com.example.memegenerator.domain.UserEntity;
import com.example.memegenerator.respository.UserRepository;
import com.example.memegenerator.service.JavaMailSender;
import com.example.memegenerator.service.UserService;
import com.example.memegenerator.shared.Utils;
import com.example.memegenerator.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.email) != null) {
			throw new UsernameNotFoundException("Email is in use");
		}

		//ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = new UserEntity();	
		
		userEntity.username = user.username;
		userEntity.email = user.email;
		userEntity.role = Role.User;
		userEntity.password = bCryptPasswordEncoder.encode(user.password);

		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDto userDto = new UserDto();
		
		userDto.username = userEntity.username;
		userDto.password = userEntity.password;
		userDto.email = userEntity.email;

		//UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

		return userDto;
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		return new User(userEntity.email, userEntity.password, userEntity.activated, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(Integer userId) {
		UserDto returnValue = new UserDto();
		Optional<UserEntity> userEntity = userRepository.findById(Integer.toUnsignedLong(userId));

		if (userEntity == null) {
			throw new UsernameNotFoundException(userId.toString());
		}

		BeanUtils.copyProperties(userEntity.get(), returnValue);

		return returnValue;
	}

	@Override
	public UserDto updateUser(Integer userId, UserDto user) {
		Optional<UserEntity> userEntity = userRepository.findById(Integer.toUnsignedLong(userId));

		if (userEntity == null) {
			throw new UsernameNotFoundException(userId.toString());
		}

		UserEntity updatedUserDetails = userRepository.save(userEntity.get());

		return new ModelMapper().map(updatedUserDetails, UserDto.class);
	}

	public List<UserDto> getUsers() {
		List<UserDto> returnValue = new ArrayList<>();

		Iterable<UserEntity> users = userRepository.findAll();

		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	@Override
	public void requestPasswordReset(String email) {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		String token = new Utils().generatePasswordResetToken(userEntity.id);

		userEntity.token = token;
		userRepository.save(userEntity);

		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(userEntity.email); 
        message.setSubject("Password reset token"); 
        message.setText("Your password reset token: " + userEntity.token);
		javaMailSender.getJavaMailSender().send(message);
	}

	@Override
	public boolean resetPassword(String token, String password) {
		boolean returnValue = false;

		if (Utils.hasTokenExpired(token)) {
			return returnValue;
		}

		UserEntity userEntity = userRepository.findByToken(token);

		if (userEntity == null) {
			return returnValue;
		}

		String hashedPassword = bCryptPasswordEncoder.encode(password);

		userEntity.password = hashedPassword;
		userEntity.token = null;

		UserEntity savedUserEntity = userRepository.save(userEntity);

		if (savedUserEntity != null && savedUserEntity.password.equalsIgnoreCase(hashedPassword)) {
			returnValue = true;
		}

		return returnValue;
	}
}
