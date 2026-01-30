package com.cartservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cartservice.dtos.*;
import com.cartservice.exceptions.ResourceNotFoundException;
import com.cartservice.exceptions.ServiceLogicException;
import com.cartservice.feigns.ProductService;
import com.cartservice.feigns.UserService;
import com.cartservice.modals.Cart;
import com.cartservice.modals.CartItem;
import com.cartservice.repositories.CartRepository;

import java.util.*;

@Component
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<ApiResponseDto<?>> addItemToCart(String userId, CartItemRequestDto requestDto)
            throws ResourceNotFoundException, ServiceLogicException {
        try {
            // Check if user exists
            Boolean userExists = Optional.ofNullable(userService.existsUserById(userId).getBody())
                    .map(ApiResponseDto::getResponse)
                    .orElse(false);

            if (!userExists) {
                throw new ResourceNotFoundException("User not found with id " + userId);
            }

            // Fetch product from product service
            ResponseEntity<ApiResponseDto<ProductDto>> productResponseEntity = productService.getProductById(requestDto.getProductId());
            ApiResponseDto<ProductDto> productResponse = productResponseEntity.getBody();

            if (productResponse == null || productResponse.getResponse() == null) {
                throw new ResourceNotFoundException("Product not found with id " + requestDto.getProductId());
            }

            // Proceed with cart logic
            Cart userCart = getCart(userId);
            Set<CartItem> userCartItems = userCart.getCartItems();
            ProductDto product = productResponse.getResponse();
            CartItem cartItem = createCartItem(userCartItems, requestDto, product);

            userCartItems.add(cartItem);
            userCart.setCartItems(userCartItems);

            cartRepository.save(userCart);

            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Item successfully added to cart!")
                            .build()
            );

        } catch (ResourceNotFoundException e) {
            log.warn("Resource not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to add item to cart", e);
            throw new ServiceLogicException("Unable to add item to cart!");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(String userId) throws ResourceNotFoundException, ServiceLogicException {
        try {

            if (Objects.requireNonNull(userService.existsUserById(userId).getBody()).getResponse()) {
                if(!cartRepository.existsByUserId(userId)) {
                    createAndSaveNewCart(userId);
                }

                Cart userCart = getCart(userId);

                CartResponseDto cartResponse = cartToCartResponseDto(userCart);

                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .response(cartResponse)
                                .build()
                );
            }
        }catch (Exception e) {
            log.error("Failed to find cart: " + e.getMessage());
            throw new ServiceLogicException("Unable to find cart!");
        }
        throw new ResourceNotFoundException("User not found with id " + userId);

    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(String userId, String productId) throws ServiceLogicException, ResourceNotFoundException {
        try {
            if(cartRepository.existsByUserId(userId)) {
                Cart userCart = cartRepository.findByUserId(userId);
                Set<CartItem> removedItemsSet = removeCartItem(userCart.getCartItems(), productId);
                userCart.setCartItems(removedItemsSet);
                cartRepository.save(userCart);

                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Item successfully removed to cart!")
                                .build()
                );

            }

        }catch (Exception e) {
            log.error("Failed to add item to cart: " + e.getMessage());
            throw new ServiceLogicException("Unable to add item to cart!");
        }
        throw new ResourceNotFoundException("No cart found for user " + userId);
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> clearCartById(String id) throws ServiceLogicException, ResourceNotFoundException {
        try {
            if(cartRepository.existsById(id)) {
                Cart userCart = cartRepository.findById(id).orElse(null);
                userCart.setCartItems(new HashSet<>());
                cartRepository.save(userCart);

                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Cart has been successfully cleared!")
                                .build()
                );

            }

        }catch (Exception e) {
            log.error("Failed to add item to cart: " + e.getMessage());
            throw new ServiceLogicException("Unable to add item to cart!");
        }
        throw new ResourceNotFoundException("No cart found for id " + id);
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getCartById(String id) throws ServiceLogicException, ResourceNotFoundException {
        try {
            Cart cart = cartRepository.findById(id).orElse(null);

            if (cart == null) {
                log.warn("Cart not found with ID: {}", id);
                throw new ResourceNotFoundException("Cart not found with ID: " + id);
            }

            CartResponseDto cartResponse = cartToCartResponseDto(cart);

            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Cart received successfully!")
                            .response(cartResponse)
                            .build()
            );

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to find cart", e);
            throw new ServiceLogicException("Unable to find cart!");
        }
    }


    private void createAndSaveNewCart(String userId) {
        if(!cartRepository.existsByUserId(userId)) {
            Cart cart = Cart.builder()
                    .userId(userId)
                    .cartItems(new HashSet<>())
                    .build();
            cartRepository.insert(cart);
        }
    }

    private CartItem getNewCartItem(CartItemRequestDto requestDto) {
        return CartItem.builder()
                .productId(requestDto.getProductId())
                .quantity(1)
                .build();
    }

    private CartItem getExistingCartItem(Set<CartItem> userCartItems, String productId) {
        List<CartItem> existingCartItems = userCartItems.stream().filter(item -> item.getProductId().equals(productId)).toList();
        if (existingCartItems.isEmpty()){
            return null;
        }
        return existingCartItems.get(0);
    }

    private Cart getCart(String userId) {
        //  if cart is not already present create new cart
        createAndSaveNewCart(userId);
        return cartRepository.findByUserId(userId);
    }

    private CartItem createCartItem(Set<CartItem> userCartItems, CartItemRequestDto requestDto, ProductDto product) {
        CartItem cartItem = getExistingCartItem(userCartItems, requestDto.getProductId());

        if (cartItem == null) {
            // Create a new cart item using product details
            cartItem = CartItem.builder()
                    .productId(product.getId())
                    .quantity(requestDto.getQuantity() > 0 ? requestDto.getQuantity() : 1)
                    .build();
        } else {
            // Adjust quantity based on request
            int quantity = requestDto.getQuantity();
            if (quantity <= 0) quantity = -1;
            if (quantity > 0) quantity = 1;
            if (cartItem.getQuantity() + quantity <= 0) quantity = 0;

            userCartItems.remove(cartItem);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        return cartItem;
    }

    private CartResponseDto cartToCartResponseDto(Cart userCart) throws ResourceNotFoundException {
        int noOfCartItems = 0;
        double subtotal = 0.0;

        Set<CartItemResponseDto> cartItems = new HashSet<>();
        for (CartItem cartItem : userCart.getCartItems()) {
            CartItemResponseDto cartItemResponse = cartItemToCartItemResponseDto(cartItem);
            noOfCartItems += cartItemResponse.getQuantity();
            subtotal += cartItemResponse.getAmount();
            cartItems.add(cartItemResponse);
        }

        return CartResponseDto.builder()
                .cartId(userCart.getId())
                .userId(userCart.getUserId())
                .cartItems(cartItems)
                .noOfCartItems(noOfCartItems)
                .subtotal(subtotal)
                .build();
    }


    private CartItemResponseDto cartItemToCartItemResponseDto(CartItem cartItem) throws ResourceNotFoundException {
        ResponseEntity<ApiResponseDto<ProductDto>> response = productService.getProductById(cartItem.getProductId());

        ProductDto product = (response != null && response.getBody() != null)
                ? response.getBody().getResponse()
                : null;

        if (product == null) {
            log.error("Product not found for ID: {}", cartItem.getProductId());
            throw new ResourceNotFoundException("Product not found for ID: " + cartItem.getProductId());
        }

        double amount = product.getPrice() * cartItem.getQuantity();

        return CartItemResponseDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(cartItem.getQuantity())
                .imageUrl(product.getImageUrl())
                .amount(amount)
                .build();
    }


    private Set<CartItem> removeCartItem(Set<CartItem> userCartItems, String productId) {
        CartItem existingCartItem = getExistingCartItem(userCartItems, productId);

        if (existingCartItem != null) {
            userCartItems.remove(existingCartItem);
        }

        return userCartItems;
    }
}
