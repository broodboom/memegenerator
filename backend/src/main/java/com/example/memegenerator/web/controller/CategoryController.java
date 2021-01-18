package com.example.memegenerator.web.controller;

import java.util.List;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.domain.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(path = "/")
    public List<Category> getCategories() {
        
        return categoryService.getCategories();
    }
}