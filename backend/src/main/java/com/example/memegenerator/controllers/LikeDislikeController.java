package com.example.memegenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memegenerator.domain.User;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.request.SocketResponseModel;

import com.example.memegenerator.service.MemeService;
import com.example.memegenerator.service.UserService;
import com.example.memegenerator.shared.dto.MemeDto;

@RestController
@RequestMapping("likedislike")
public class LikeDislikeController {

    @Autowired
    MemeService memeService;

    @Autowired
    UserService userService;

    @MessageMapping("/")
    //@SendTo("/likedislike/")
    public SocketResponseModel likedislike(@RequestBody SocketResponseModel response) {
        MemeModel meme = memeService.getMemeById(response.memeId);
        
        if(response.isUpvote){
            meme.likes++;

            User user = meme.user;

            userService.updateUserPoints(user.id, 1);
            userService.updateUserPoints(response.userId, 1);
        }else{
            meme.dislikes++;
            userService.updateUserPoints(response.userId, 1);
        }

        MemeDto memeDto = new MemeDto();
        memeDto.title = meme.title;
        memeDto.description = meme.description;
        memeDto.imageblob = meme.imageblob;
        memeDto.likes = meme.likes;
        memeDto.dislikes = meme.dislikes;
        memeDto.id = response.memeId;

        memeService.updateMeme(memeDto);

        return response;
    }
}
