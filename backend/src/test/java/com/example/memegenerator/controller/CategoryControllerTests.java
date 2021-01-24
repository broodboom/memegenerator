package com.example.memegenerator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.memegenerator.domain.service.CategoryService;
import com.example.memegenerator.web.controller.CategoryController;
import com.example.memegenerator.web.dto.CategoryDto;

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

import static org.hamcrest.Matchers.hasItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
    CategoryController.class
})
public class CategoryControllerTests {

    @Autowired
    private CategoryController controller;

    @MockBean
    private CategoryService categoryService;

    public final static String categoryTitle = "testtitle";

    @Autowired
	private MockMvc mockMvc;	

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_categories() throws Exception {

        int generations = new Random().nextInt(10);

        List<CategoryDto> list = new ArrayList<CategoryDto>();

        for (int i = 0; i < generations; i++) {
            list.add(new CategoryDto() {
                {
                    setTitle(CategoryControllerTests.categoryTitle);
                }
            });
        }

        when(categoryService.getCategories()).thenReturn(list);

        this.mockMvc.perform(get("/category").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title", hasItem(CategoryControllerTests.categoryTitle)));
    }
}