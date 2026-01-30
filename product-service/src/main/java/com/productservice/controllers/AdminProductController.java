package com.productservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.productservice.dtos.ApiResponseDto;
import com.productservice.dtos.ProductRequestDto;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.exceptions.ServiceLogicException;
import com.productservice.services.ProductService;

@RestController
@RequestMapping("/admin/product")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException{
        return productService.getAllProducts();
    }

    @PostMapping("/add")

@PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<ApiResponseDto<?>> addProduct(@RequestBody ProductRequestDto requestDto) throws ServiceLogicException, ResourceNotFoundException {
        return productService.addProduct(requestDto);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<ApiResponseDto<?>> editProduct(@RequestParam String productId, @RequestBody ProductRequestDto requestDto) throws ServiceLogicException, ResourceNotFoundException {
        return productService.editProduct(productId, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<?>> deleteProduct(@RequestParam String productId)
            throws ServiceLogicException, ResourceNotFoundException {
        return productService.deleteProduct(productId);
    }


}
