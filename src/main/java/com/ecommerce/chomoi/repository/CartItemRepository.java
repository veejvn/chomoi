package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.CartItem;
import com.ecommerce.chomoi.entities.embeddedIds.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , CartItemId> {
    boolean existsById(CartItemId id);
    @Override
    Optional<CartItem> findById(CartItemId id);
}
