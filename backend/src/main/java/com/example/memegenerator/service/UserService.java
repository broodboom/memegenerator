package com.example.memegenerator.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.memegenerator.domain.User;
import com.example.memegenerator.repository.UserRepository;
import com.example.memegenerator.security.Role;
import com.example.memegenerator.shared.dto.UserDto;
import com.example.memegenerator.security.UserDetailsAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    public ResponseEntity<String> createUser(UserDto dto) {

        Optional<User> userOptionalEmail = userRepository.findByEmail(dto.email);

        Optional<User> userOptionalUsername = userRepository.findByUsername(dto.username);

        // Check if email is in use
        if (userOptionalEmail.isPresent())
            return ResponseEntity.badRequest().body("Email is already in use");

        // Check if username is in use
        if (userOptionalUsername.isPresent())
            return ResponseEntity.badRequest().body("Username is already in use");

        // Set user properties
        User user = new User();
        user.username = dto.username;
        user.email = dto.email;
        user.role = Role.User;
        user.password = bCryptPasswordEncoder.encode(dto.password);
        user.confirmationToken = UUID.randomUUID().toString();

        // Save user
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(user.email);
        message.setSubject("Thank you for signing up");
        message.setText("Your confirmation token: " + user.confirmationToken);
        javaMailSender.getJavaMailSender().send(message);

        return ResponseEntity.ok("User " + user.username + " has been created");
    }

    public ResponseEntity<String> updateUser(User oldUser, UserDto dto) {

        Optional<User> userOptional = userRepository.findById(oldUser.id);

        if (!userOptional.isPresent())
            return ResponseEntity.badRequest().body("User with id " + oldUser.id + " not found");

        // Todo: check if email is already in use

        // Set user properties
        userOptional.get().email = dto.email;
        userOptional.get().username = oldUser.username;
        userOptional.get().password = bCryptPasswordEncoder.encode(dto.password);

        // Save user
        userRepository.save(userOptional.get());

        return ResponseEntity.ok("User " + userOptional.get().username + " has been updated");
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent())
            return new User();

        return user.get();
    }

    public UserDto getUser(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public UserDto getUserByUsername(String username) {

        User user = userRepository.findByUsername(username).orElse(null);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsAdapter(userRepository.findUserByUsername(username));
    }

    public UserDto getUserByUserId(long userId) {

        UserDto returnValue = new UserDto();
        Optional<User> stupidJavaOptional = userRepository.findById(userId);

        if (!stupidJavaOptional.isPresent()) {
            throw new UsernameNotFoundException(Long.toString(userId));
        }

        User user = stupidJavaOptional.get();

        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public List<UserDto> getUsers() {

        List<UserDto> returnValue = new ArrayList<>();

        Iterable<User> users = userRepository.findAll();

        for (User userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    public void requestPasswordReset(String email) {

        Optional<User> stupidJavaOptional = userRepository.findByEmail(email);

        if (!stupidJavaOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        User user = stupidJavaOptional.get();

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

    public boolean resetPassword(String token, String password) {

        boolean returnValue = false;

        Optional<User> stupidJavaOptional = userRepository.findByToken(token);

        if (!stupidJavaOptional.isPresent()) {
            return returnValue;
        }

        User user = stupidJavaOptional.get();

        String hashedPassword = bCryptPasswordEncoder.encode(password);

        user.password = hashedPassword;
        user.token = null;

        User savedUserEntity = userRepository.save(user);

        if (savedUserEntity != null && savedUserEntity.password.equalsIgnoreCase(hashedPassword)) {
            returnValue = true;
        }

        return returnValue;
    }
}