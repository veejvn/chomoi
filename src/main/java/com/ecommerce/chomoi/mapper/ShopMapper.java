package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.dto.shop.ShopUpdateRequest;
import com.ecommerce.chomoi.entities.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopResponse toShopResponse(Shop shop);
}

