package com.orderservice.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orderservice.dtos.ApiResponseDto;
import com.orderservice.dtos.UserDto;

@FeignClient("USER-SERVICE")
public interface UserService {

    @GetMapping("/user/get/byId")
    ResponseEntity<ApiResponseDto<UserDto>> getUserById(@RequestParam String id);

}
