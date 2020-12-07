package com.example.memegenerator.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.shared.dto.MemeDto;


import com.example.memegenerator.service.MemeService;

@RestController
@RequestMapping("meme")
public class MemeController {

    @Autowired
    MemeService memeService;

    @PostMapping(path = "/")
    public void createMeme(@RequestBody MemeModel meme)
    {
        MemeDto memeDto = new MemeDto();
        memeDto.title = meme.title;
        memeDto.description = meme.description;
        memeDto.imageblob = meme.imageblob;
        memeDto.likes = 0;
        memeDto.dislikes = 0;

        // Create meme
        memeService.createMeme(memeDto);
    }

    @GetMapping(path = "/{id}")
    public MemeModel getMemeById(@PathVariable long id){
        return memeService.getMemeById(id);
    }

    @PostMapping(path = "/update/{meme}")
    public void updateMeme(@RequestBody MemeModel meme){
        MemeDto memeDto = new MemeDto();
        memeDto.title = meme.title;
        memeDto.description = meme.description;
        memeDto.imageblob = meme.imageblob;
        memeDto.likes = meme.likes;
        memeDto.dislikes = meme.dislikes;

        memeService.updateMeme(memeDto);
    }
}
