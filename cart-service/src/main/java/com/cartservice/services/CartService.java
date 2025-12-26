package com.cartservice.services;

import org.springframework.http.ResponseEntity;

import com.cartservice.dtos.ApiResponseDto;
import com.cartservice.dtos.CartItemRequestDto;
import com.cartservice.exceptions.ResourceNotFoundException;
import com.cartservice.exceptions.ServiceLogicException;

public interface CartService {
    ResponseEntity<ApiResponseDto<?>> addItemToCart(String userId, CartItemRequestDto requestDto) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(String userId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(String userId, String productId) throws ServiceLogicException, ResourceNotFoundException;
    ResponseEntity<ApiResponseDto<?>> clearCartById(String id) throws ServiceLogicException, ResourceNotFoundException;
    ResponseEntity<ApiResponseDto<?>> getCartById(String id) throws ServiceLogicException, ResourceNotFoundException;
}
