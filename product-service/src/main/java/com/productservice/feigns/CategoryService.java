package com.productservice.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.productservice.dtos.ApiResponseDto;
import com.productservice.dtos.CategoryDto;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryService {

    @GetMapping("/category/get/byId")
    ResponseEntity<ApiResponseDto<CategoryDto>> getCategoryById(@RequestParam String id);

}
