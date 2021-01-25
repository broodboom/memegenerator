package com.example.memegenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.example.memegenerator.domain.service.impl.UserServiceImpl;
import com.example.memegenerator.web.controller.UserController;
import com.example.memegenerator.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.memegenerator.web.dto.SmallUserDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class UserControllerTests {

    @Autowired
    private UserController controller;

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
    public void creates_user() throws Exception {

        String mockValue = "acbdef";
        UserDto userDtoMock = new UserDto() {
            {
                setUsername(mockValue);
                setPassword(mockValue);
                setEmail(mockValue);
            }
        };

        when(userService.createUser(any())).thenReturn(userDtoMock);

        mockMvc.perform(MockMvcRequestBuilders.post("/user").content(asJsonString(userDtoMock))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void updates_user() throws Exception {

        String mockValue = "acbdef";
        UserDto userDtoMock = new UserDto() {
            {
                setUsername(mockValue);
                setPassword(mockValue);
                setEmail(mockValue);
            }
        };

        when(userService.updateUser(any())).thenReturn(userDtoMock);

        mockMvc.perform(MockMvcRequestBuilders.put("/user").content(asJsonString(userDtoMock))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void activates_user() throws Exception {

        this.mockMvc.perform(get("/user/activate/1/2").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void gets_userinfo() throws Exception {

        String mockValue = "abcdef";
        SmallUserDto userDtoMock = new SmallUserDto() {
            {
                setUsername(mockValue);
                setEmail(mockValue);
            }
        };

        when(userService.getUserById(anyLong())).thenReturn(userDtoMock);

        var resultActions = this.mockMvc
                .perform(get("/user/1").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var mvcResult = resultActions.andReturn();
        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String username = (String) dataList.get(0).get("username");

        assertEquals(mockValue, username);
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