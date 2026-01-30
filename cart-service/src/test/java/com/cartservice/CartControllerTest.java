package com.cartservice;

import com.cartservice.controllers.CartController;
import com.cartservice.dtos.ApiResponseDto;
import com.cartservice.dtos.CartItemRequestDto;
import com.cartservice.services.CartService;

import org.springframework.http.MediaType;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

   
    @WithMockUser(roles = "USER")
    void testAddItemToCart() throws Exception {
        CartItemRequestDto requestDto = new CartItemRequestDto(null, 0);
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Item added", null);

        Mockito.when(cartService.addItemToCart(Mockito.anyString(), Mockito.any()))
                .thenReturn(ResponseEntity.ok(responseDto));

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":\"123\",\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item added"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetCartItemsByUser() throws Exception {
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Cart fetched", null);

        Mockito.when(cartService.getCartItemsByUser(Mockito.anyString()))
                .thenReturn(ResponseEntity.ok(responseDto));

        mockMvc.perform(get("/cart/get/byUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cart fetched"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testRemoveCartItemFromCart() throws Exception {
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Item removed", null);

        Mockito.when(cartService.removeCartItemFromCart(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity.ok(responseDto));

        mockMvc.perform(delete("/cart/remove")
                .param("productId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item removed"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetCartById() throws Exception {
        ApiResponseDto<?> responseDto = new ApiResponseDto(true, "Cart found", null);

        Mockito.when(cartService.getCartById("cart123"))
                .thenReturn(ResponseEntity.ok(responseDto));

        mockMvc.perform(get("/cart/get/byId")
                .param("id", "cart123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cart found"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testClearCartById() throws Exception {
        ApiResponseDto<String> responseDto = new ApiResponseDto(true, "Cart cleared", null);

        Mockito.when(cartService.clearCartById("cart123"))
                .thenReturn(ResponseEntity.ok(responseDto));

        mockMvc.perform(delete("/cart/clear/byId")
                .param("id", "cart123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cart cleared"));
    }
}
