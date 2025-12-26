package com.productservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.productservice.dtos.ApiResponseDto;
import com.productservice.dtos.ProductRequestDto;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.exceptions.ServiceLogicException;


@Service
public interface ProductService {
    ResponseEntity<ApiResponseDto<?>> addProduct(ProductRequestDto requestDto) throws ServiceLogicException, ResourceNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getProductById(String productId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getProductByCategory(String categoryId) throws ServiceLogicException, ResourceNotFoundException;

    ResponseEntity<ApiResponseDto<?>> searchProducts(String searchKey) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> editProduct(String productId, ProductRequestDto requestDto) throws ServiceLogicException, ResourceNotFoundException;

	ResponseEntity<ApiResponseDto<?>> deleteProduct(String productId) throws ServiceLogicException, ResourceNotFoundException;
}
