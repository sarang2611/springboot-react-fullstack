package com.categoryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.categoryservice.dtos.ApiResponseDto;
import com.categoryservice.dtos.CategoryRequestDto;
import com.categoryservice.exceptions.CategoryAlreadyExistsException;
import com.categoryservice.exceptions.ServiceLogicException;
import com.categoryservice.services.CategoryService;

@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException {
        return categoryService.createCategory(categoryRequestDto);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> editCategory(@RequestParam String categoryId, @RequestBody CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException {
        return categoryService.editCategory(categoryId, categoryRequestDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> deleteCategory(@RequestParam String categoryId) throws ServiceLogicException, CategoryAlreadyExistsException {
        return categoryService.deleteCategory(categoryId);
    }

}
