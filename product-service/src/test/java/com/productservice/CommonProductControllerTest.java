package com.productservice;

import com.productservice.controllers.CommonProductController;
import com.productservice.dtos.ApiResponseDto;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.exceptions.ServiceLogicException;
import com.productservice.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CommonProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private CommonProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    void testGetAllProducts() throws ServiceLogicException {
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "All products", null);
        when(productService.getAllProducts()).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All products", response.getBody().getMessage());
    }

    @Test
    void testGetProductById() throws ServiceLogicException {
        String productId = "prod123";
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Product found", null);
        when(productService.getProductById(productId)).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = productController.getProductById(productId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product found", response.getBody().getMessage());
    }

    @Test
    void testGetProductByCategory() throws ServiceLogicException, ResourceNotFoundException {
        String categoryId = "cat123";
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Category products", null);
        when(productService.getProductByCategory(categoryId)).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = productController.getProductByCategory(categoryId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Category products", response.getBody().getMessage());
    }

    @Test
    void testSearchProducts() throws ServiceLogicException {
        String searchKey = "laptop";
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Search results", null);
        when(productService.searchProducts(searchKey)).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = productController.searchProducts(searchKey);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Search results", response.getBody().getMessage());
    }
}
