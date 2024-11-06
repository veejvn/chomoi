package com.ecommerce.chomoi.dto.cart_item;

import com.ecommerce.chomoi.dto.product.ProductResponse;
import com.ecommerce.chomoi.dto.product.ProductTagResponse;
import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.entities.*;
import com.ecommerce.chomoi.enums.ProductStatus;
import com.ecommerce.chomoi.enums.ShopStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    String skuId;             // SKU ID from CartItem
    String productSlug;       // Slug of the product
    int quantity;          // Quantity of the CartItem
    BigDecimal price;             // Price of the SKU
    int stock;             // Stock of the SKU
    String image;             // Image URL of the SKU
    SKU sku;
    ProductResponse product;
    ShopResponse shop;


    @Data
    public static class ProductResponse {
        String id;
        String name;
        String slug;
        String thumbnail;
        int sold;
        Double rating;
        BigDecimal minPrice;
        BigDecimal maxPrice;
        List<Variation> variations;
    }
}
