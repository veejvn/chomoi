package com.ecommerce.chomoi.dto.cart;

import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import com.ecommerce.chomoi.dto.product.ProductResponse;
import com.ecommerce.chomoi.entities.Category;
import com.ecommerce.chomoi.entities.Image;
import com.ecommerce.chomoi.entities.SKU;
import com.ecommerce.chomoi.entities.Variation;
import com.ecommerce.chomoi.enums.ProductStatus;
import com.ecommerce.chomoi.enums.ShopStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    String id;
    List<CartItemResponse> cartItems;
}
