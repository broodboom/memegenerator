package com.example.memegenerator.service.impl;

import java.util.List;

import com.example.memegenerator.domain.Category;
import com.example.memegenerator.repository.CategoryRepository;
import com.example.memegenerator.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        List<Category> all = (List<Category>) categoryRepository.findAll();

        return all;
    }

}