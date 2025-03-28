package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.SKU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SKURepository extends JpaRepository<SKU, String> {
    Optional<SKU> findById(String sku_id);
}
