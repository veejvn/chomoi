package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Product;
import com.ecommerce.chomoi.entities.Shop;
import com.ecommerce.chomoi.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.shop = :shop AND p.status != com.ecommerce.chomoi.enums.ProductStatus.DELETED")
    Optional<Product> findByIdAndShop(@Param("id") String id,
                                      @Param("shop") Shop shop);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.status != com.ecommerce.chomoi.enums.ProductStatus.DELETED")
    Optional<Product> findByIdAndAmin(@Param("id") String id);

    @Query("SELECT p FROM Product p WHERE p.shop = :shop AND p.status != com.ecommerce.chomoi.enums.ProductStatus.DELETED")
    List<Product> findAllByShop(Shop shop);

    @Query("SELECT p FROM Product p WHERE p.id = :idOrSlug OR p.slug = :idOrSlug AND p.status = com.ecommerce.chomoi.enums.ProductStatus.ACTIVE")
    Optional<Product> findByIdOrSlug(@Param("idOrSlug") String idOrSlug);

    @Query("SELECT p FROM Product p WHERE p.status = com.ecommerce.chomoi.enums.ProductStatus.ACTIVE")
    Page<Product> findForHome(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    Page<Product> adminGetDashboard(@Param("status") ProductStatus status, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.status = com.ecommerce.chomoi.enums.ProductStatus.ACTIVE AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Product> search(@Param("query") String query,
                         Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.shop.id = :shopId AND p.status = com.ecommerce.chomoi.enums.ProductStatus.ACTIVE")
    Page<Product> findByShopId(@Param("shopId") String shopId, Pageable pageable);
}
