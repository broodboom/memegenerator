package com.example.memegenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.example.memegenerator.domain.service.MemeService;
import com.example.memegenerator.domain.service.UserService;
import com.example.memegenerator.web.controller.LikeDislikeController;
import com.example.memegenerator.web.dto.MemeDto;
import com.example.memegenerator.web.dto.SocketResponseDto;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class LikeDislikeControllerTests {

    @Autowired
    private LikeDislikeController controller;

    @MockBean
    private MemeService memeService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_categories() throws Exception {

        int memeId = 12345;
        String memeTitle = "testtitle";
        MemeDto memeDtoMock = new MemeDto() {
            {
                setTitle(memeTitle);
            }
        };
        SocketResponseDto socketResponseDtoMock = new SocketResponseDto() {
            {
                setMemeId(memeId);
            }
        };

        when(memeService.getMemeById(anyLong())).thenReturn(memeDtoMock);
        when(memeService.updateMeme(any())).thenReturn(memeDtoMock);

        var parsedSocketResponseDtoMock = JsonPath.parse(socketResponseDtoMock);
        var bytesParsedSocketResponseDtoMock = parsedSocketResponseDtoMock.json().toString().getBytes();

        var resultActions = this.mockMvc.perform(
                post("/likedislike/").contentType(MediaType.APPLICATION_JSON).content(bytesParsedSocketResponseDtoMock))
                .andExpect(status().isOk());

        var mvcResult = resultActions.andReturn();
        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String memeIdFromJson = (String) dataList.get(0).get("memeId");

        assertEquals(Integer.toString(memeId), memeIdFromJson);
    }
}