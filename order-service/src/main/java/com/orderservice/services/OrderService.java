package com.orderservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderservice.dtos.ApiResponseDto;
import com.orderservice.dtos.OrderRequestDto;
import com.orderservice.exceptions.ResourceNotFoundException;
import com.orderservice.exceptions.ServiceLogicException;

@Service
public interface OrderService {
    ResponseEntity<ApiResponseDto<?>> createOrder(String token, OrderRequestDto request) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getOrdersByUser(String userId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, ResourceNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getAllOrders() throws ServiceLogicException;
}
