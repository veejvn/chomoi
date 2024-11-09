package com.ecommerce.chomoi.controller;

import java.util.List;

import com.cloudinary.Api;
import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.dto.shop.ShopUpdateRequest;
import com.ecommerce.chomoi.entities.Address;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.security.SecurityUtil;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.auth.AuthUpgradeToShop;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Shop;
import com.ecommerce.chomoi.service.AccountService;
import com.ecommerce.chomoi.service.ShopService;
import com.ecommerce.chomoi.util.jwt.BaseJWTUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shops")
@PreAuthorize("authenticated()")
public class ShopController {

    @Autowired
    private ShopService shopService;

    private SecurityUtil securityUtil;

    @PreAuthorize("permitAll()")
    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<ShopResponse>> getShop(@PathVariable String shopId) {
        ShopResponse shop = shopService.getShopById(shopId);
        ApiResponse<ShopResponse> response = ApiResponse.<ShopResponse>builder()
                .code("shop-s-01")
                .message("Get shop successfully")
                .data(shop)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('SHOP')")
    @GetMapping("/owner")
    public ResponseEntity<ApiResponse<ShopResponse>> getShopByOwner(){
        ShopResponse shop = shopService.getShopByOwner();
        ApiResponse<ShopResponse> response = ApiResponse.<ShopResponse>builder()
                .code("shop-s-02")
                .message("Get shop by owner successfully")
                .data(shop)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('SHOP')")
    @PutMapping
    public ResponseEntity<ApiResponse<ShopResponse>> updateShopInfo(@Valid @RequestBody ShopUpdateRequest shopUpdateRequest){
        ShopResponse shop = shopService.updateShopInfo(shopUpdateRequest);
        ApiResponse<ShopResponse> response = ApiResponse.<ShopResponse>builder()
                .code("shop-s-03")
                .message("Update shop info successfully")
                .data(shop)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //Get Shop from user
    @PreAuthorize("permitAll()")
    @GetMapping("/{shopId}/details")
    public ResponseEntity<ApiResponse<ShopResponse>> getShopById(@PathVariable String shopId) {
        ShopResponse shop = shopService.getShopById(shopId);
        ApiResponse<ShopResponse> response = ApiResponse.<ShopResponse>builder()
                .code("shop-s-01")
                .message("Get shop successfully")
                .data(shop)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}