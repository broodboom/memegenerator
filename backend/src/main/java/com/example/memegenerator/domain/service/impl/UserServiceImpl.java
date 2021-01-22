package com.example.memegenerator.domain.service.impl;

import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.ejb.DuplicateKeyException;

import com.example.memegenerator.data.entity.User;
import com.example.memegenerator.domain.service.JavaMailSender;
import com.example.memegenerator.domain.service.UserService;
import com.example.memegenerator.security.UserDetailsAdapter;
import com.example.memegenerator.data.repository.UserRepository;
import com.example.memegenerator.web.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;
    private final ModelMapper modelMapper;

    public UserDto createUser(UserDto userDto) throws NoSuchElementException {

        userRepository.findByEmail(userDto.email)
                .orElseThrow(() -> new NoSuchElementException("Email is already in use"));
        userRepository.findByUsername(userDto.username)
                .orElseThrow(() -> new NoSuchElementException("Username is already in use"));

        User user = modelMapper.map(userDto, User.class);
        user.password = bCryptPasswordEncoder.encode(userDto.password);
        user.confirmationToken = this.randomInt();

        User savedUser = userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(savedUser.email);
        message.setSubject("Thank you for signing up");

        String url = "http://localhost:8080/user/activate/" + savedUser.id + "/" + savedUser.confirmationToken;

        message.setText("Click here to activate your account: " + url);

        javaMailSender.getJavaMailSender().send(message);

        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) throws NoSuchElementException, DuplicateKeyException {

        User user = userRepository.findByEmail(userDto.email)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        if (user.id != userDto.id) {

            throw new DuplicateKeyException("Email is already in use");
        }

        user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserDto getUserById(long userId) throws NoSuchElementException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new UserDetailsAdapter(userRepository.findUserByUsername(username));
    }

    public void requestPasswordReset(String email) throws NoSuchElementException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        byte[] array = new byte[10];
        new Random().nextBytes(array);
        String token = new String(array, Charset.forName("UTF-8"));

        user.token = token;
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(user.email);
        message.setSubject("Password reset token");
        message.setText("Your password reset token: " + user.token);
        javaMailSender.getJavaMailSender().send(message);
    }

    public void resetPassword(String confirmationToken, String password) throws NoSuchElementException {

        User user = userRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        user.password = bCryptPasswordEncoder.encode(password);
        user.token = null;

        userRepository.save(user);
    }

    public void activateUser(Long userId, String confirmationToken) throws NoSuchElementException {

        User user = userRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        if (user.id != userId) {
            throw new NoSuchElementException(USER_NOT_FOUND);
        }

        user.activated = true;

        userRepository.save(user);
    }

    private int randomInt() {

        return new Random().nextInt(9000) + 1000;
    }

    public void updateUserPoints(Long userId, int pointsToAdd) throws NoSuchElementException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        user.points = user.points + pointsToAdd;

        userRepository.save(user);
    }
}
