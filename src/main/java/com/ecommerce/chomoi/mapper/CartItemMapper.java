package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.cart_item.CartItemRequest;
import com.ecommerce.chomoi.dto.cart_item.CartItemResponse;
import com.ecommerce.chomoi.entities.CartItem;
import com.ecommerce.chomoi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemResponse.ProductResponse toProductResponse(Product product);

    @Mapping(target = "skuId", source = "id.skuId")
    @Mapping(target = "productSlug", source = "sku.product.slug")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "sku.price")
    @Mapping(target = "stock", source = "sku.stock")
    @Mapping(target = "image", source = "sku.image")
    @Mapping(target = "product", source = "sku.product")
    @Mapping(target = "shop", source = "sku.product.shop")
    CartItemResponse toCartItemResponse(CartItem cartItem);
    CartItem toCartItem (CartItemRequest request);
}
