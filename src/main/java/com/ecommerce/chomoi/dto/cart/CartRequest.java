package com.ecommerce.chomoi.dto.cart;

import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    String id;
    Set<CartItemResponse> cartItems;
}
