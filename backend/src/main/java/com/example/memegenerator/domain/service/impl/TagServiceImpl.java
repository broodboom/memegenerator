package com.example.memegenerator.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.data.repository.TagRepository;
import com.example.memegenerator.domain.service.TagService;
import com.example.memegenerator.web.dto.TagDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagDto createTag(TagDto tagDto) {

        Tag tag = modelMapper.map(tagDto, Tag.class);

        Tag savedTag = tagRepository.save(tag);

        return modelMapper.map(savedTag, TagDto.class);
    }

    public List<TagDto> getTags() {

        List<Tag> allTags = tagRepository.findAll();

        return allTags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }
}