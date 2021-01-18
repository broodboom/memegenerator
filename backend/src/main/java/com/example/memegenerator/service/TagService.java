package com.example.memegenerator.service;

import java.util.List;

import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.shared.dto.TagDto;

public interface TagService {
    Tag createTag(TagDto tag);

    List<Tag> getTags();
}