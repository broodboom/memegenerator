package com.example.memegenerator.web.controller;

import java.util.List;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.domain.service.TagService;
import com.example.memegenerator.web.dto.TagDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping(path = "/create/{tag}")
    public ResponseEntity<?> createTag(Tag tag) {

        TagDto tagDto = new TagDto();

        tagDto.title = tag.title;

        tagService.createTag(tagDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Tag>> getTags() {

        return new ResponseEntity<List<Tag>>(tagService.getTags(), HttpStatus.OK);
    }
}