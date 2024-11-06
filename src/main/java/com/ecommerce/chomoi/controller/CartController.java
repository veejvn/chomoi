package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.account.AccountRequest;
import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.cart.CartResponse;
import com.ecommerce.chomoi.dto.cart_item.CartItemRequest;
import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Cart;
import com.ecommerce.chomoi.security.SecurityUtil;
import com.ecommerce.chomoi.service.CartItemService;
import com.ecommerce.chomoi.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final SecurityUtil securityUtil;
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

    @PostMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> addToCart(@RequestBody @Valid CartItemRequest request){
        ApiResponse<CartItemResponse> apiResponse = ApiResponse.<CartItemResponse>builder()
                .code("cart-s-02")
                .message("Add to cart successfully!")
                .data(cartItemService.addToCart(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> updateQuantity(@RequestBody @Valid CartItemRequest request){
        ApiResponse<CartItemResponse> apiResponse = ApiResponse.<CartItemResponse>builder()
                .code("cart-s-02")
                .message("Update quatity successfully!")
                .data(cartItemService.updateQuantity(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{sku_id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> deleteCartItem(@PathVariable("sku_id") String sku_id) {
            cartItemService.deleteCartItem(sku_id);
            ApiResponse<CartItemResponse> apiResponse = ApiResponse.<CartItemResponse>builder()
                    .code("cart-s-03")
                    .message("Deleted item successfully!")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
