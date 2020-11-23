package com.example.assignment2.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class MainController {
    @GetMapping()
    ResponseEntity<String> message() {
        return new ResponseEntity("" +
                "<a href='/rekening'>rekening</a><br />" +
                "<a href='/rekeninghouder'>rekeninghouder</a>"
                , HttpStatus.OK);
    }
}