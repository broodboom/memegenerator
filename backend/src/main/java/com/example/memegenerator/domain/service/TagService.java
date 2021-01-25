package com.example.memegenerator.domain.service;

import java.util.List;
import java.util.Set;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.web.dto.TagDto;

public interface TagService {
    
    TagDto createTag(TagDto tag);

    List<TagDto> getTags();
    Set<Tag> getTagsForMeme(long memeId);
}