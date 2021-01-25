package com.example.memegenerator.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.domain.service.CategoryService;
import com.example.memegenerator.web.dto.CategoryDto;

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
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void gets_categories() {

        int generations = new Random().nextInt(9) + 1;
        List<Category> categoryList = new ArrayList<Category>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            categoryList.add(new Category() {
                {
                    setTitle(mockTitle);
                }
            });
        }

        when(categoryRepository.findAll()).thenReturn(categoryList);
        
        List<CategoryDto> result = categoryService.getCategories();

        assertEquals(result.size(), generations);
    }
}