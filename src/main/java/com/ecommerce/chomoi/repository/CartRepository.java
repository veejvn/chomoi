package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Cart;
import com.ecommerce.chomoi.entities.embeddedIds.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByAccountId(String accountId);
}
