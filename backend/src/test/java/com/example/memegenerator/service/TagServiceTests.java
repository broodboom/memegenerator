package com.example.memegenerator.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.data.repository.TagRepository;
import com.example.memegenerator.domain.service.TagService;
import com.example.memegenerator.web.dto.TagDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration()
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    @Test
    public void creates_tag() {

        String testValue = "abc";

        TagDto tagDto = new TagDto() {
            {
                setTitle(testValue);
            }
        };

        Tag tag = new Tag() {
            {
                setTitle(testValue);
            }
        };

        when(tagRepository.save(any())).thenReturn(tag);

        TagDto result = tagService.createTag(tagDto);

        assertEquals(result.getTitle(), tagDto.getTitle());
    }

    @Test
    public void gets_tags() {

        int generations = new Random().nextInt(9) + 1;
        List<Tag> tagList = new ArrayList<Tag>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            tagList.add(new Tag() {
                {
                    setTitle(mockTitle);
                }
            });
        }

        when(tagRepository.findAll()).thenReturn(tagList);
        
        List<TagDto> result = tagService.getTags();

        assertEquals(result.size(), generations);
    }
}