package com.ecommerce.chomoi.dto.cart_item;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {

    String skuId;             // SKU ID from CartItem
    String productSlug;       // Slug of the product
    String quantity;          // Quantity of the CartItem
    String price;             // Price of the SKU
    String stock;             // Stock of the SKU
    String image;             // Image URL of the SKU

    // Add any additional fields from the SKU or CartItem if needed
}
