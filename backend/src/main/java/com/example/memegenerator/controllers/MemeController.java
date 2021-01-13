package com.example.memegenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.example.memegenerator.domain.Meme;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.shared.dto.MemeDto;

import com.example.memegenerator.service.MemeService;

@RestController
@RequestMapping("meme")
public class MemeController {

    @Autowired
    MemeService memeService;

    @GetMapping(path = "/")
    public List<Meme> getMemes() {
        return memeService.getMemes();
    }

    // @MessageMapping("/")
    // @SendTo("/meme/")
    // public String test(String message) throws Exception{
    //     return "testString";
    // }

    @PostMapping(path = "/")
    public void createMeme(@RequestParam("imageblob") MultipartFile imageblob, String title, String userId) throws IOException {
        MemeDto memeDto = new MemeDto();
        memeDto.title = title;
        memeDto.description = "";
        memeDto.imageblob = imageblob.getBytes();
        memeDto.likes = 0;
        memeDto.dislikes = 0;

        long userIdLong = Long.parseLong(userId);

        // Create meme
        memeService.createMeme(memeDto, userIdLong);
    }

    @GetMapping(path = "/{id}")
    public MemeModel getMemeById(@PathVariable long id) {
        return memeService.getMemeById(id);
    }

    @PutMapping(path = "/update/{meme}")
    public void updateMeme(@RequestBody MemeModel meme) {
        MemeDto memeDto = new MemeDto();
        memeDto.title = meme.title;
        memeDto.description = meme.description;
        memeDto.imageblob = meme.imageblob;
        memeDto.likes = meme.likes;
        memeDto.dislikes = meme.dislikes;

        memeService.updateMeme(memeDto);
    }

    @GetMapping(path="/category/{id}")
    public List<Meme> getFilteredMemes(@PathVariable long id){
        return memeService.getFilteredMemes(id);
    }
}
