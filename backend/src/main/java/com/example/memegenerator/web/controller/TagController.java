package com.example.memegenerator.web.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.domain.service.TagService;
import com.example.memegenerator.web.dto.TagDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping(path = "/create/{tag}")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {

        return new ResponseEntity<TagDto>(tagService.createTag(tagDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<TagDto>> getTags() {

        return new ResponseEntity<List<TagDto>>(tagService.getTags(), HttpStatus.OK);
    }

    @GetMapping(path = "/tag/{memeId}")
    public ResponseEntity<Set<Tag>> getMemeTags(@PathVariable long memeId){
        return new ResponseEntity<Set<Tag>>(tagService.getTagsForMeme(memeId), HttpStatus.OK);
    }
}