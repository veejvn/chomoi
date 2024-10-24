package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.api.PagedResponse;
import com.ecommerce.chomoi.dto.product.*;
import com.ecommerce.chomoi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/home")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<PagedResponse<ProductTagResponse>>> getHomeProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductTagResponse> pagedProducts = productService.getHome(page, size);

        PagedResponse<ProductTagResponse> pagedResponseProducts = PagedResponse.<ProductTagResponse>builder()
                .totalPages(pagedProducts.getTotalPages())
                .page(pagedProducts.getNumber())
                .totalElements(pagedProducts.getTotalElements())
                .content(pagedProducts.getContent())
                .build();

        ApiResponse<PagedResponse<ProductTagResponse>> apiResponse = ApiResponse.<PagedResponse<ProductTagResponse>>builder()
                .code("product-s-01")
                .message("Get home products successfully")
                .data(pagedResponseProducts)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<ProductTagResponse>>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String query) {
        Page<ProductTagResponse> pagedProducts = productService.search(page, size, query);
        PagedResponse<ProductTagResponse> pagedResponseProducts = PagedResponse.<ProductTagResponse>builder()
                .totalPages(pagedProducts.getTotalPages())
                .page(pagedProducts.getNumber())
                .totalElements(pagedProducts.getTotalElements())
                .content(pagedProducts.getContent())
                .build();
        ApiResponse<PagedResponse<ProductTagResponse>> apiResponse = ApiResponse.<PagedResponse<ProductTagResponse>>builder()
                .code("product-s-02")
                .message("Search products successfully")
                .data(pagedResponseProducts)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{productIdOrSlug}")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(
            @PathVariable String productIdOrSlug) {
        ProductResponse productResponse = productService.getByIdOrSlug(productIdOrSlug);
        ApiResponse<ProductResponse> apiResponse = ApiResponse.<ProductResponse>builder()
                .code("product-s-03")
                .message("Get home products successfully")
                .data(productResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasRole('SHOP')")
    @PostMapping("/shop")
    public ResponseEntity<ApiResponse<ProductForShopResponse>> create(@Valid @RequestBody ProductAddRequest productAddRequest) {
        ProductForShopResponse product = productService.addProduct(productAddRequest);
        ApiResponse<ProductForShopResponse> response = ApiResponse.<ProductForShopResponse>builder()
                .code("product-s-04")
                .message("Create product successfully")
                .data(product)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('SHOP')")
    @GetMapping("/shop")
    public ResponseEntity<ApiResponse<List<ProductForShopResponse>>> getAllByShop() {
        List<ProductForShopResponse> productList = productService.getAllByShop();
        ApiResponse<List<ProductForShopResponse>> response = ApiResponse.<List<ProductForShopResponse>>builder()
                .code("product-s-05")
                .message("Get product by id successfully")
                .data(productList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('SHOP')")
    @GetMapping("/shop/{productId}")
    public ResponseEntity<ApiResponse<ProductForShopResponse>> getByShopAndId(@PathVariable String productId) {
        ProductForShopResponse product = productService.getByShopAndId(productId);
        ApiResponse<ProductForShopResponse> response = ApiResponse.<ProductForShopResponse>builder()
                .code("product-s-06")
                .message("Get product by id successfully")
                .data(product)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('SHOP')")
    @PutMapping("/shop/{productId}")
    public ResponseEntity<ApiResponse<ProductForShopResponse>> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        ProductForShopResponse updatedProduct = productService.updateProduct(productId, productUpdateRequest);
        ApiResponse<ProductForShopResponse> response = ApiResponse.<ProductForShopResponse>builder()
                .code("product-s-07")
                .message("Update product successfully")
                .data(updatedProduct)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('SHOP')")
    @PutMapping("/shop/{productId}/change-status")
    public ResponseEntity<ApiResponse<Void>> shopChangeStatus(
            @PathVariable String productId,
            @Valid @RequestBody ProductChangeStatusRequest productChangeStatusRequest) {
        productService.shopChangeStatus(productId, productChangeStatusRequest.getStatus());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code("product-s-09")
                .message("Change status successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{productId}/change-status")
    public ResponseEntity<ApiResponse<Void>> adminChangeStatus(
            @PathVariable String productId,
            @Valid @RequestBody ProductChangeStatusRequest productChangeStatusRequest) {
        productService.adminChangeStatus(productId, productChangeStatusRequest.getStatus());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code("product-s-09")
                .message("Change status successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public ResponseEntity<ApiResponse<PagedResponse<ProductTagResponse>>> getAdminDashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "PENDING") String status) {
        Page<ProductTagResponse> pagedProducts = productService.adminGetDashboard(page, size, status);
        PagedResponse<ProductTagResponse> pagedResponseProducts = PagedResponse.<ProductTagResponse>builder()
                .totalPages(pagedProducts.getTotalPages())
                .page(pagedProducts.getNumber())
                .totalElements(pagedProducts.getTotalElements())
                .content(pagedProducts.getContent())
                .build();
        ApiResponse<PagedResponse<ProductTagResponse>> apiResponse = ApiResponse.<PagedResponse<ProductTagResponse>>builder()
                .code("product-s-10")
                .message("Get home products successfully")
                .data(pagedResponseProducts)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
