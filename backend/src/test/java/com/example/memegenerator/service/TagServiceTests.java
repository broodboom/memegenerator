package com.example.memegenerator.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

        when(tagRepository.save(any())).thenReturn(Optional.of(tag));

        TagDto result = tagService.createTag(tagDto);

        assertEquals(result.getTitle(), tagDto.getTitle());
    }

    public void gets_tags() {
        
    }
}