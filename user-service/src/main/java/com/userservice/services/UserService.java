package com.userservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.userservice.dtos.ApiResponseDto;
import com.userservice.exceptions.ServiceLogicException;
import com.userservice.exceptions.UserNotFoundException;


@Service
public interface UserService {
    ResponseEntity<ApiResponseDto<?>> existsUserById(String userId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getUserById(String id) throws ServiceLogicException, UserNotFoundException;
}
