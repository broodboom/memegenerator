package com.example.memegenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.memegenerator.domain.service.impl.MemeServiceImpl;
import com.example.memegenerator.web.controller.MemeController;
import com.example.memegenerator.web.dto.MemeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class MemeControllerTests {

    @Autowired
    private MemeController controller;

    @MockBean
    private MemeServiceImpl memeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_memes() throws Exception {

        int generations = new Random().nextInt(9) + 1;
        List<MemeDto> memeList = new ArrayList<MemeDto>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            memeList.add(new MemeDto() {
                {
                    setTitle(mockTitle);
                }
            });
        }

        when(memeService.getMemes()).thenReturn(memeList);

        var resultActions = this.mockMvc
                .perform(get("/meme/").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var mvcResult = resultActions.andReturn();
        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");

        assertEquals(mockTitle, title);
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void creates_meme() throws Exception {

        String mockTitle = "acbdef";
        MemeDto memeDtoMock = new MemeDto() {
            {
                setTitle(mockTitle);
            }
        };

        when(memeService.createMeme(any(), anyLong())).thenReturn(memeDtoMock);

        MockMultipartFile fileMock = new MockMultipartFile("imageblob",
                "Hello, World!".getBytes());

        var resultActions = this.mockMvc
                .perform(multipart("/meme/")
                .file(fileMock)
                .param("title", mockTitle)
                .param("userId", "1")
                .param("tags", "[]")
                .param("description", "description")
                .param("categoryId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        var mvcResult = resultActions.andReturn();
        var json = mvcResult.getResponse().getContentAsString();

        String title = JsonPath.read(json, "$.title");

        assertEquals(mockTitle, title);
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_meme_by_id() throws Exception {

        String mockTitle = "acbdef";
        MemeDto memeDtoMock = new MemeDto() {
            {
                setTitle(mockTitle);
            }
        };

        when(memeService.getMemeById(anyInt())).thenReturn(memeDtoMock);

        this.mockMvc.perform(get("/meme/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void updates_meme() throws Exception {

        String mockTitle = "acbdef";
        MemeDto memeDtoMock = new MemeDto() {
            {
                setTitle(mockTitle);
            }
        };

        when(memeService.updateMeme(any())).thenReturn(memeDtoMock);

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/meme/update").content(asJsonString(memeDtoMock))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        String title = JsonPath.read(json, "$.title");

        assertEquals(mockTitle, title);
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_filtered_memes() throws Exception {

        int generations = new Random().nextInt(9) + 1;
        List<MemeDto> memeList = new ArrayList<MemeDto>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            memeList.add(new MemeDto() {
                {
                    setTitle(mockTitle);
                }
            });
        }

        when(memeService.getFilteredMemes(anyLong())).thenReturn(memeList);

        var resultActions = this.mockMvc
                .perform(get("/meme/category/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var mvcResult = resultActions.andReturn();
        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");

        assertEquals(mockTitle, title);
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void user_allowed_to_create_meme() throws Exception {

        when(memeService.userAllowedToCreate(anyLong())).thenReturn(true);

        this.mockMvc.perform(get("/meme/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}