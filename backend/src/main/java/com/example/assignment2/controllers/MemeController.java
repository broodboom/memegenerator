package com.example.assignment2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment2.request.MemeModel;
import com.example.assignment2.shared.dto.MemeDto;


import com.example.assignment2.service.UserService;

@RestController
@RequestMapping("user")
public class MemeController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/")
    public void createMeme(@RequestBody MemeModel meme)
    {


        MemeDto memeDto = new MemeDto();
        memeDto.title = meme.title;
        memeDto.description = meme.description;
        memeDto.imageblob = meme.imageblob;
        memeDto.likes = 0;
        memeDto.dislikes = 0;

        memeService.createMeme(memeDto);
        // Create user
        UserDto createdUser = userService.createUser(userDto);

        // Show newly created user to the client
        UserRest newUser = new UserRest();
        newUser.username = createdUser.username;
        newUser.email = createdUser.email;

        return newUser;
    }
}
