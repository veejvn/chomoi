package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.cart.CartResponse;
import com.ecommerce.chomoi.entities.Cart;
import com.ecommerce.chomoi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart() {
        // Fetch the cart for the logged-in user
        CartResponse cartResponse = cartService.get();

        // Return the cart data in ApiResponse
        ApiResponse<CartResponse> apiResponse = ApiResponse.<CartResponse>builder()
                .code("cart-s-01")
                .message("Get cart successfully")
                .data(cartResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
