package com.example.memegenerator.domain.service;

import java.util.List;

import com.example.memegenerator.web.dto.CategoryDto;

public interface CategoryService {

    List<CategoryDto> getCategories();
}