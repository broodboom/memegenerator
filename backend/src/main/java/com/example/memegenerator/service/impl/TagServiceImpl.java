package com.example.memegenerator.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.example.memegenerator.domain.Tag;
import com.example.memegenerator.repository.TagRepository;
import com.example.memegenerator.service.MemeService;
import com.example.memegenerator.service.TagService;
import com.example.memegenerator.shared.dto.TagDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    
    @Autowired
    TagRepository tagRepository;

    @Autowired
    MemeService memeService;

    public void createTag(TagDto tag) {
        Tag dbTag = new Tag();
        dbTag.title = tag.title;
        dbTag.createdat = new Timestamp(System.currentTimeMillis());

        tagRepository.save(dbTag);
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> all = (List<Tag>) tagRepository.findAll();

        return all;
    }

}