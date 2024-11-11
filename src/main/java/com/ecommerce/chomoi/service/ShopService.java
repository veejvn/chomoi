package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.dto.shop.ShopUpdateRequest;
import com.ecommerce.chomoi.entities.Shop;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.OrderMapper;
import com.ecommerce.chomoi.mapper.ShopMapper;
import com.ecommerce.chomoi.repository.OrderRepository;
import com.ecommerce.chomoi.repository.ShopRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final SecurityUtil securityUtil;
    private final ShopMapper shopMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public ShopResponse getShopById(String shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Shop was not found", "shop-e-01"));
        return shopMapper.toShopResponse(shop);
    }

    public ShopResponse getShopByOwner() {
        Shop shop = securityUtil.getShop();
        return shopMapper.toShopResponse(shop);
    }

    public ShopResponse updateShopInfo(ShopUpdateRequest shopUpdateRequest) {
        Shop shop = securityUtil.getShop();
        shop.setName(shopUpdateRequest.getName());
        shop.setAvatar(shopUpdateRequest.getAvatar());
        shop.setCoverImage(shopUpdateRequest.getCoverImage());
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }
}
