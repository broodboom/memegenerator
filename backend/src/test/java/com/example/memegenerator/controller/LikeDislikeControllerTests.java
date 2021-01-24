package com.example.memegenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.memegenerator.domain.service.impl.MemeServiceImpl;
import com.example.memegenerator.domain.service.impl.UserServiceImpl;
import com.example.memegenerator.web.controller.LikeDislikeController;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class LikeDislikeControllerTests {

    @Autowired
    private LikeDislikeController controller;

    @MockBean
    private MemeServiceImpl memeService;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void like_dislike() {

    }
}