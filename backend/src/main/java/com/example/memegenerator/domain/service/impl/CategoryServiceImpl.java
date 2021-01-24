package com.example.memegenerator.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.memegenerator.data.entity.Category;
import com.example.memegenerator.data.repository.CategoryRepository;
import com.example.memegenerator.domain.service.CategoryService;
import com.example.memegenerator.web.dto.CategoryDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    
    /** 
     * @return List<CategoryDto>
     */
    public List<CategoryDto> getCategories() {

        List<Category> allCategories = categoryRepository.findAll();

        return allCategories.stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }
}