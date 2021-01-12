package com.example.memegenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.request.SocketResponseModel;

import com.example.memegenerator.service.MemeService;
import com.example.memegenerator.shared.dto.MemeDto;

@RestController
@RequestMapping("likedislike")
public class LikeDislikeController {

    @Autowired
    MemeService memeService;

    @MessageMapping("/")
    //@SendTo("/likedislike/")
    public SocketResponseModel likedislike(@RequestBody SocketResponseModel response) {
        MemeModel meme = memeService.getMemeById(response.memeId);
        
        if(response.isUpvote){
            meme.likes++;
        }else{
            meme.dislikes++;
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
