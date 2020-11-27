package com.example.assignment2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
	@GetMapping("/test")
    ResponseEntity<String> message() {
        return new ResponseEntity<String>("Hoi", HttpStatus.OK);
    }
}
