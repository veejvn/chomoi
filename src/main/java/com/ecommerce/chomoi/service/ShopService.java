package com.ecommerce.chomoi.service;

import java.util.Optional;

import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.dto.shop.ShopUpdateRequest;
import com.ecommerce.chomoi.entities.Address;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.ShopMapper;
import com.ecommerce.chomoi.repository.AddressRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.chomoi.dto.auth.AuthUpgradeToShop;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Shop;
import com.ecommerce.chomoi.repository.AccountRepository;
import com.ecommerce.chomoi.repository.ShopRepository;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final SecurityUtil securityUtil;
    private final ShopMapper shopMapper;
    private final AddressRepository addressRepository;

    public ShopResponse getShopById(String shopId) {
        Shop shop = shopRepository.findById(shopId)
            .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Shop was not found", "shop-e-01"));
        return shopMapper.toShopResponse(shop);
    }

    public ShopResponse getShopByOwner(){
        Shop shop = securityUtil.getShop();
        return shopMapper.toShopResponse(shop);
    }

    public ShopResponse updateShopInfo(ShopUpdateRequest shopUpdateRequest){
        Shop shop = securityUtil.getShop();
        shop.setName(shopUpdateRequest.getName());
        shop.setAvatar(shopUpdateRequest.getAvatar());
        shop.setCoverImage(shopUpdateRequest.getCoverImage());
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }
}
