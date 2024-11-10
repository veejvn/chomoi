package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.order.OrderRequest;
import com.ecommerce.chomoi.dto.order.OrderResponse;
import com.ecommerce.chomoi.dto.order.OrderStatusRequest;
import com.ecommerce.chomoi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody @Valid OrderRequest request) {
        ApiResponse<OrderResponse> apiResponse = ApiResponse.<OrderResponse>builder()
                .code("order-s-01")
                .message("Create order successfully")
                .data(orderService.createOrder(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAll() {
        ApiResponse<List<OrderResponse>> apiResponse = ApiResponse.<List<OrderResponse>>builder()
                .code("order-s-02")
                .message("Get orders successfully")
                .data(orderService.getAll())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getByOrderStatus(@RequestParam String status) {
        ApiResponse<List<OrderResponse>> apiResponse = ApiResponse.<List<OrderResponse>>builder()
                .code("order-s-03")
                .message("Get orders successfully")
                .data(orderService.getByStatus(status))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> get(@PathVariable String id) {
        ApiResponse<OrderResponse> apiResponse = ApiResponse.<OrderResponse>builder()
                .code("order-s-04")
                .message("Get order successfully")
                .data(orderService.get(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // Putmapping
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> delete(@PathVariable String id) {
        orderService.cancel(id);

        ApiResponse<OrderResponse> apiResponse = ApiResponse.<OrderResponse>builder()
                .code("order-s-05")
                .message("Delete order successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasRole('SHOP')")
    @GetMapping("/shop")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getListOrderOfShop() {
        ApiResponse<List<OrderResponse>> apiResponse = ApiResponse.<List<OrderResponse>>builder()
                .code("order-s-06")
                .message("Get orders of shop successfully")
                .data(orderService.listOrderOfShop())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasRole('SHOP')")
    @PutMapping("/{id}/shop")
    public ResponseEntity<ApiResponse<OrderResponse>> shopUpdateOrderStatus(@PathVariable String id, @RequestBody @Valid OrderStatusRequest request) {
        ApiResponse<OrderResponse> apiResponse = ApiResponse.<OrderResponse>builder()
                .code("order-s-07")
                .message("Update order status successfully")
                .data(orderService.shopUpdateOrderStatus(id, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasRole('SHOP')")
    @GetMapping("/shop/status")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getListOrderByStatusOfShop(@RequestParam String status) {
        ApiResponse<List<OrderResponse>> apiResponse = ApiResponse.<List<OrderResponse>>builder()
                .code("order-s-08")
                .message("Get orders of shop successfully")
                .data(orderService.getListOrderByStatusOfShop(status))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{id}/user")
    public ResponseEntity<ApiResponse<OrderResponse>> userUpdateOrderStatus(@PathVariable String id, @RequestBody @Valid OrderStatusRequest request) {
        ApiResponse<OrderResponse> apiResponse = ApiResponse.<OrderResponse>builder()
                .code("order-s-09")
                .message("You updated order status successfully")
                .data(orderService.userUpdateOrderStatus(id, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
