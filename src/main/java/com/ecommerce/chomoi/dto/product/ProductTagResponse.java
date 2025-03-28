package com.ecommerce.chomoi.dto.product;

import com.ecommerce.chomoi.entities.Shop;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTagResponse {
    String id;
    String name;
    String slug;
    String thumbnail;
    int sold;
    Double rating;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Shop shop;
}