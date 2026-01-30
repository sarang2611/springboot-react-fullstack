package com.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.userservice.dtos.ApiResponseDto;
import com.userservice.exceptions.ServiceLogicException;
import com.userservice.exceptions.UserNotFoundException;
import com.userservice.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/exists/byId")
    public ResponseEntity<ApiResponseDto<?>> existsUserById(@RequestParam String userId) throws ServiceLogicException {
        return userService.existsUserById(userId);
    }

    @GetMapping("/get/byId")
    public ResponseEntity<ApiResponseDto<?>> getUserById(@RequestParam String id) throws UserNotFoundException, ServiceLogicException {
        return userService.getUserById(id);
    }
}
