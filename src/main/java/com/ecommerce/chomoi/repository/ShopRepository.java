package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface ShopRepository extends JpaRepository<Shop, String> {
    Optional<Shop> findByAccount(Account account);
    Optional<Shop> findByAccountId(String accountId);
}
