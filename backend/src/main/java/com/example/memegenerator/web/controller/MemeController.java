package com.example.memegenerator.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.web.dto.MemeDto;
import com.example.memegenerator.web.dto.TagDto;
import com.google.gson.Gson;
import com.example.memegenerator.domain.service.MemeService;

@RestController
@RequestMapping("meme")
@RequiredArgsConstructor
public class MemeController {

    private final MemeService memeService;

    @GetMapping(path = "/")
    public ResponseEntity<List<MemeDto>> getMemes() {

        return new ResponseEntity<List<MemeDto>>(memeService.getMemes(), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<MemeDto> createMeme(@RequestParam("imageblob") MultipartFile imageblob, String title,
            String userId, @RequestParam("tags") String tagsString, long categoryId, String description) {

        MemeDto memeDto = new MemeDto();
        memeDto.title = title;
        memeDto.description = description;
        memeDto.likes = 0;
        memeDto.dislikes = 0;
        memeDto.categoryId = categoryId;

        try {

            memeDto.imageblob = imageblob.getBytes();
        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Gson gson = new Gson();
        TagDto[] tags = gson.fromJson(tagsString, TagDto[].class);
        long userIdLong = Long.parseLong(userId);

        memeDto.tags = new Tag[tags.length];

        for (int i = 0; i < tags.length; i++) {

            Tag newTag = new Tag();
            newTag.id = tags[i].id;
            newTag.title = tags[i].title;
            memeDto.tags[i] = newTag;
        }

        try {

            return new ResponseEntity<>(memeService.createMeme(memeDto, userIdLong), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{memeId}")
    public ResponseEntity<MemeDto> getMemeById(@PathVariable long memeId) {

        try {

            return new ResponseEntity<MemeDto>(memeService.getMemeById(memeId), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<MemeDto> updateMeme(@Valid @RequestBody MemeDto memeDto) {

        try {

            return new ResponseEntity<MemeDto>(memeService.updateMeme(memeDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<List<MemeDto>> getFilteredMemes(@PathVariable long categoryId) {

        try {

            return new ResponseEntity<List<MemeDto>>(memeService.getFilteredMemes(categoryId), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path= "/tags/{tags}")
    public ResponseEntity<List<MemeDto>> getFilteredTagMemes(@PathVariable List<Long> tags){
        try{
            return new ResponseEntity<List<MemeDto>>(memeService.getFilteredMemesTag(tags), HttpStatus.OK);
        }  catch (NoSuchElementException e) {

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }

    @GetMapping(path = "/checkAllowed/{userId}")
    public ResponseEntity<Boolean> userAllowedToCreateMeme(@PathVariable long userId){

        try {

            return new ResponseEntity<>(memeService.userAllowedToCreate(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
