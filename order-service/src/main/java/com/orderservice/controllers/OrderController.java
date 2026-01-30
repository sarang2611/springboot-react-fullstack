package com.orderservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.orderservice.dtos.ApiResponseDto;
import com.orderservice.dtos.OrderRequestDto;
import com.orderservice.exceptions.ResourceNotFoundException;
import com.orderservice.exceptions.ServiceLogicException;
import com.orderservice.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
	public
    ResponseEntity<ApiResponseDto<?>> createOrder(Authentication authentication, @RequestBody OrderRequestDto request) throws ResourceNotFoundException, ServiceLogicException {
        System.out.println(authentication.getCredentials().toString());
        return orderService.createOrder(authentication.getCredentials().toString(), request);
    }

    @GetMapping("/get/byUser")
    @PreAuthorize("hasRole('ROLE_USER')")
	public
    ResponseEntity<ApiResponseDto<?>> getOrdersByUser(Authentication authentication) throws ResourceNotFoundException, ServiceLogicException {
        return orderService.getOrdersByUser(authentication.getPrincipal().toString());
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public
    ResponseEntity<ApiResponseDto<?>> getAllOrders() throws ResourceNotFoundException, ServiceLogicException {
        return orderService.getAllOrders();
    }

    @PatchMapping("/cancel")
    @PreAuthorize("hasRole('ROLE_USER')")
	public
    ResponseEntity<ApiResponseDto<?>> cancelOrder(@RequestParam String orderId) throws ResourceNotFoundException, ServiceLogicException {
        return orderService.cancelOrder(orderId);
    }


}
