package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Order;
import com.ecommerce.chomoi.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByBuyerId(String buyerId);

    Optional<Order> findByIdAndBuyerId(String orderId, String buyerId);

    List<Order> findByShopId(String shopId);

    List<Order> findByBuyerIdAndStatus(String buyerId, OrderStatus status);

    List<Order> findByShopIdAndStatus(String shopId, OrderStatus status);
}
