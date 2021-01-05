package com.example.memegenerator.controllers;

import java.io.IOException;
import java.util.List;

import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.service.TagService;
import com.example.memegenerator.shared.dto.TagDto;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void createTag(Tag tag) throws IOException {
        TagDto tagDto = new TagDto();
        tagDto.title = tag.title;

        tagService.createTag(tagDto);
    }

    @GetMapping(path = "/")
    public List<Tag> getTags() {
        return tagService.getTags();
    }
}