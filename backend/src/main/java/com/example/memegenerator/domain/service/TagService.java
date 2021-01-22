package com.example.memegenerator.domain.service;

import java.util.List;

import com.example.memegenerator.web.dto.TagDto;

public interface TagService {
    
    TagDto createTag(TagDto tag);

    List<TagDto> getTags();
}