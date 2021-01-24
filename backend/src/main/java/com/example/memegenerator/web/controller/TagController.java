package com.example.memegenerator.web.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.memegenerator.domain.service.TagService;
import com.example.memegenerator.web.dto.TagDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    
    /** 
     * @param tagDto
     * @return ResponseEntity<TagDto>
     */
    @PostMapping(path = "/create/{tag}")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {

        return new ResponseEntity<TagDto>(tagService.createTag(tagDto), HttpStatus.CREATED);
    }

    
    /** 
     * @return ResponseEntity<List<TagDto>>
     */
    @GetMapping(path = "/")
    public ResponseEntity<List<TagDto>> getTags() {

        return new ResponseEntity<List<TagDto>>(tagService.getTags(), HttpStatus.OK);
    }
}