package com.example.memegenerator.web.controller;

import java.util.List;

import com.example.memegenerator.domain.service.CategoryService;
import com.example.memegenerator.web.dto.CategoryDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(path = "/")
    public ResponseEntity<List<CategoryDto>> getCategories() {

        return new ResponseEntity<List<CategoryDto>>(categoryService.getCategories(), HttpStatus.OK);
    }
}