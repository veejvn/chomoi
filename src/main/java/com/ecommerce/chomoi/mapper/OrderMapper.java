package com.ecommerce.chomoi.mapper;


import com.ecommerce.chomoi.dto.order.OrderResponse;
import com.ecommerce.chomoi.entities.Order;
import com.ecommerce.chomoi.entities.OrderItem;
import com.ecommerce.chomoi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    
    OrderResponse.OrderItemDTO.OrderItemProductResponseDTO toOrderItemProductResponseDTO(Product product);

    @Mapping(target = "product", source = "sku.product")
    OrderResponse.OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toListOrderResponse(List<Order> orders);
}
