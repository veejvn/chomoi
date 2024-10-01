package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.ProductStatus;
import com.ecommerce.chomoi.enums.ShopStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    String id;

    @Column(name = "product_name")
    String name;

    @Column(name = "product_description")
    String description;

    @Column(name = "product_price")
    Double price;

    @Column(name = "product_rating")
    Double rating;

    @Column(name = "product_inventory")
    Integer inventory;

    @Column(name = "product_sold")
    Integer sold;

    @Column(name = "product_thumbnail")
    String thumbnail;

    @Column(name = "product_video")
    String video;

    @Column(name = "product_slug")
    String slug;

    //relationship

    //Use Enum Status to make relationship between Product and Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @PrePersist
    protected void onCreate() {
        this.status = ProductStatus.PENDING;
    }
}
