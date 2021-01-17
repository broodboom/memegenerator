package com.example.memegenerator.domain.service;

import java.util.List;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.web.dto.TagDto;

public interface TagService {
    
    void createTag(TagDto tag);

    List<Tag> getTags();
}