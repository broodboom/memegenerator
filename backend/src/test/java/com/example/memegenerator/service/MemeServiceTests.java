package com.example.memegenerator.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.entity.Meme;
import com.example.memegenerator.data.entity.Tag;
import com.example.memegenerator.data.entity.User;
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

import java.sql.Timestamp;
import java.util.Date;

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
    public void creates_meme() {

        String testValue = "abc";

        MemeDto memeDto = new MemeDto() {
            {
                setTitle(testValue);
                setTags(new Tag[0]);
            }
        };

        Meme meme = new Meme() {
            {
                setTitle(testValue);
                setTags(new HashSet<Tag>());
            }
        };

        Category category = new Category() {
            {
                setTitle(testValue);
            }
        };

        User user = new User() {
            {
                setUsername(testValue);
                setPassword(testValue);
                setEmail(testValue);
            }
        };

        when(memeRepository.save(any())).thenReturn(meme);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        MemeDto result = memeService.createMeme(memeDto, (long) 2);

        assertEquals(result.getTitle(), memeDto.getTitle());
    }

    @Test
    public void gets_memes() {

        int generations = new Random().nextInt(9) + 1;
        List<Meme> memeList = new ArrayList<Meme>();
        String mockTitle = "testtitle";

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        for (int i = 0; i < generations; i++) {
            memeList.add(new Meme() {
                {
                    setTitle(mockTitle);
                    setCreatedat(ts);
                }
            });
        }

        when(memeRepository.findAll()).thenReturn(memeList);

        List<MemeDto> result = memeService.getMemes();

        assertEquals(result.size(), generations);
    }

    @Test
    public void gets_meme() {

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        String mockTitle = "testtitle";

        Meme meme = new Meme() {
            {
                setTitle(mockTitle);
                setCreatedat(ts);
            }
        };

        when(memeRepository.findById(anyLong())).thenReturn(Optional.of(meme));

        MemeDto result = memeService.getMemeById(anyLong());

        assertEquals(result.getTitle(), meme.getTitle());
    }

    @Test
    public void updates_meme() {

        String testValue = "abc";

        Meme meme = new Meme() {
            {
                setTitle(testValue);
            }
        };

        MemeDto memeDto = new MemeDto() {
            {
                setTitle(testValue);
                setTags(new Tag[0]);
            }
        };

        when(memeRepository.findById(any())).thenReturn(Optional.of(meme));
        when(memeRepository.save(any())).thenReturn(meme);

        MemeDto result = memeService.updateMeme(memeDto);

        assertEquals(result.getTitle(), memeDto.getTitle());
    }
    
    @Test
    public void gets_filtered_memes() {

        int generations = new Random().nextInt(9) + 1;
        List<Meme> memeList = new ArrayList<Meme>();
        String mockTitle = "testtitle";

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        for (int i = 0; i < generations; i++) {
            memeList.add(new Meme() {
                {
                    setTitle(mockTitle);
                    setCreatedat(ts);
                }
            });
        }

        Category category = new Category() {
            {
                setTitle(mockTitle);
            }
        };

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        when(memeRepository.findAll()).thenReturn(memeList);

        List<MemeDto> result = memeService.getMemes();

        assertEquals(result.size(), generations);
    }

    @Test
    public void gets_filtered_memes_tags() {

        int generations = new Random().nextInt(9) + 1;
        List<Meme> memeList = new ArrayList<Meme>();
        String mockTitle = "testtitle";

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        for (int i = 0; i < generations; i++) {
            memeList.add(new Meme() {
                {
                    setTitle(mockTitle);
                    setCreatedat(ts);
                }
            });
        }

        Category category = new Category() {
            {
                setTitle(mockTitle);
            }
        };

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        when(memeRepository.findAll()).thenReturn(memeList);

        List<MemeDto> result = memeService.getFilteredMemesTag(any());

        assertEquals(result.size(), generations);
    }

    @Test
    public void user_allowed_to_create() {

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        String testValue = "testtitle";

        Meme meme = new Meme() {
            {
                setTitle(testValue);
                setTags(new HashSet<Tag>());
            }
        };

        Category category = new Category() {
            {
                setTitle(testValue);
            }
        };

        User user = new User() {
            {
                setUsername(testValue);
                setPassword(testValue);
                setEmail(testValue);
            }
        };

        when(memeRepository.countAddedRecordsTodayByUser(any(), anyLong())).thenReturn(anyInt());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        boolean result = memeService.userAllowedToCreate(anyLong());
    }
}