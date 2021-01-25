package com.example.memegenerator.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.data.repository.MemeRepository;
import com.example.memegenerator.data.repository.TagRepository;
import com.example.memegenerator.data.repository.UserRepository;
import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.web.dto.MemeDto;

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
public class MemeServiceTests {

    @Autowired
    private MemeService memeService;

    @MockBean
    private MemeRepository memeRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TagRepository tagRepository;

    @Test
    public void gets_memes() {

        int generations = new Random().nextInt(9) + 1;
        List<Meme> memeList = new ArrayList<Meme>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            memeList.add(new Meme() {
                {
                    setTitle(mockTitle);
                }
            });
        }

        when(memeRepository.findAll()).thenReturn(memeList);
        
        List<MemeDto> result = memeService.getCategories();

        assertEquals(result.size(), generations);
    }
}