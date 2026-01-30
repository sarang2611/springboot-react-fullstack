package com.orderservice;


import com.orderservice.controllers.OrderController;
import com.orderservice.dtos.ApiResponseDto;
import com.orderservice.dtos.OrderRequestDto;
import com.orderservice.exceptions.ResourceNotFoundException;
import com.orderservice.exceptions.ServiceLogicException;
import com.orderservice.services.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Order created", null);

        when(authentication.getCredentials()).thenReturn("user123");
        when(orderService.createOrder("user123", request)).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = orderController.createOrder(authentication, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order created", response.getBody().getMessage());
    }

    @Test
    void testGetOrdersByUser() throws Exception {
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Orders fetched", null);

        when(authentication.getPrincipal()).thenReturn("user123");
        when(orderService.getOrdersByUser("user123")).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = orderController.getOrdersByUser(authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Orders fetched", response.getBody().getMessage());
    }

    @Test
    void testGetAllOrders() throws Exception {
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "All orders", null);

        when(orderService.getAllOrders()).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = orderController.getAllOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All orders", response.getBody().getMessage());
    }

    @Test
    void testCancelOrder() throws Exception {
        String orderId = "order123";
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Order cancelled", null);

        when(orderService.cancelOrder(orderId)).thenReturn(ResponseEntity.ok(responseDto));

        ResponseEntity<ApiResponseDto<?>> response = orderController.cancelOrder(orderId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order cancelled", response.getBody().getMessage());
    }
}

