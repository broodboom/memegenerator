package com.example.memegenerator.domain.service.impl;

import java.util.List;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.domain.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {

        return (List<Category>) categoryRepository.findAll();
    }

}