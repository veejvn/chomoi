package com.ecommerce.chomoi.dto.shop;


import com.ecommerce.chomoi.dto.product.ProductResponse;
import com.ecommerce.chomoi.entities.Address;
import com.ecommerce.chomoi.enums.ShopStatus;
import lombok.*;


@Data
public class ShopResponse {
    String id;

    String name;

    String avatar;

    String coverImage;

    Double rating;

    ShopStatus status;
}
