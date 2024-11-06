package com.ecommerce.chomoi.dto.cart_item;

import com.ecommerce.chomoi.entities.Cart;
import com.ecommerce.chomoi.entities.SKU;
import com.ecommerce.chomoi.entities.embeddedIds.CartItemId;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    //CartItemId id;
    String skuId;
    int quantity;
}
