package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.cart.CartResponse;
import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Cart;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.CartItemMapper;
import com.ecommerce.chomoi.repository.AccountRepository;
import com.ecommerce.chomoi.repository.CartRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartItemMapper cartItemMapper;
    SecurityUtil securityUtil;
    AccountRepository accountRepository;

    public CartResponse get() {
        String accountId = securityUtil.getAccountId();  // Get the logged-in user's account ID

        // Try to fetch the cart for the logged-in user
        Cart cart = cartRepository.findByAccountId(accountId).orElseGet(() -> {
            // If no cart found, create a new one
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Account not found", "account-e-01"));

            // Create a new cart and associate it with the logged-in user
            Cart newCart = Cart.builder()
                    .account(account)
                    .build();

            // Save the new cart to the database
            return cartRepository.save(newCart);
        });

        // Handle cart items and create message based on cart contents
        Set<CartItemResponse> cartItemResponses = (cart.getCartItems() == null || cart.getCartItems().isEmpty())
                ? Set.of()  // If cart items are empty, return an empty set
                : cart.getCartItems().stream()
                .map(cartItemMapper::toCartItemResponse)
                .collect(Collectors.toSet());

        // Return the CartResponse
        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponses)
                .build();
    }
}
