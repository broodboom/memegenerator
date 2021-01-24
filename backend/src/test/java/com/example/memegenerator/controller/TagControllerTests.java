package com.example.memegenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.memegenerator.domain.service.impl.TagServiceImpl;
import com.example.memegenerator.web.controller.TagController;
import com.example.memegenerator.web.dto.TagDto;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class TagControllerTests {

    @Autowired
    private TagController controller;

    @MockBean
    private TagServiceImpl tagService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_tags() throws Exception {

        int generations = new Random().nextInt(9) + 1;
        List<TagDto> tagList = new ArrayList<TagDto>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            tagList.add(new TagDto() {
                {
                    setTitle(mockTitle);
                }
            });
        }

        when(tagService.getTags()).thenReturn(tagList);

        var resultActions = this.mockMvc
                .perform(get("/tag/").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var mvcResult = resultActions.andReturn();
        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");

        assertEquals(mockTitle, title);
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void creates_tag() throws Exception {

        String mockTitle = "acbdef";
        TagDto tagDtoMock = new TagDto() {
            {
                setTitle(mockTitle);
            }
        };

        when(tagService.createTag(any())).thenReturn(tagDtoMock);

        this.mockMvc.perform(post("/tag/create/abc")).andExpect(status().isOk());
    }
}