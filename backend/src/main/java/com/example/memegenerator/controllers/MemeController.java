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
import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.request.MemeModel;
import com.example.memegenerator.shared.dto.MemeDto;
import com.example.memegenerator.shared.dto.TagDto;
import com.google.gson.Gson;
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

    @PostMapping(path = "/")
    public Meme createMeme(@RequestParam("imageblob") MultipartFile imageblob, String title, String userId,
            @RequestParam("tags") String tagsString, String description) throws IOException {
        MemeDto memeDto = new MemeDto();
        memeDto.title = title;
        memeDto.description = description;
        memeDto.imageblob = imageblob.getBytes();
        memeDto.likes = 0;
        memeDto.dislikes = 0;
        Gson gson = new Gson();
        TagDto[] tags = gson.fromJson(tagsString, TagDto[].class);
        memeDto.tags = new Tag[tags.length];
        int i = 0;
        for (TagDto element : tags) {
            Tag newTag = new Tag();
            newTag.id = element.id;
            newTag.title = element.title;
            memeDto.tags[i] = newTag;
            i = i + 1;
        }

        long userIdLong = Long.parseLong(userId);

        // Create meme
        return memeService.createMeme(memeDto, userIdLong);
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

    @GetMapping(path = "/checkAllowed/{id}")
    public Boolean userAllowedToCreateMeme(@PathVariable long id){
        return memeService.userAllowedToCreate(id);
    }
}
