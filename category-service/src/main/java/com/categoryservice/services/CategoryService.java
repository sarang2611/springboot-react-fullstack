package com.categoryservice.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.categoryservice.dtos.ApiResponseDto;
import com.categoryservice.dtos.CategoryRequestDto;
import com.categoryservice.exceptions.CategoryAlreadyExistsException;
import com.categoryservice.exceptions.ServiceLogicException;

@Service
public interface CategoryService {

    ResponseEntity<ApiResponseDto<?>> getAllCategories() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getCategoryById(String categoryId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> createCategory(CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException;

    ResponseEntity<ApiResponseDto<?>> editCategory(String categoryId, CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException;

    ResponseEntity<ApiResponseDto<?>> deleteCategory(String categoryId) throws ServiceLogicException;
}
