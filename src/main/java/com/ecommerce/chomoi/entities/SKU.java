package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.SKUStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SKU {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sku_id")
    String id;

    @Column(name = "sku_price", nullable = false)
    BigDecimal price;

    @Column(name = "sku_stock", nullable = false)
    Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SKUStatus status;

    @Column(name = "sku_weight", nullable = false)
    String weight;

    @Column(name = "sku_is_default", nullable = false)
    Boolean isDefault;

    @Column(name = "sku_slug", nullable = true)
    String slug;

    @Column(name = "sku_variation", nullable = false)
    String variation;

    @Column(name = "sku_image", nullable = false)
    String image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sku", orphanRemoval = true)
    @JsonIgnore
    Set<Review> reviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sku", orphanRemoval = true)
    @JsonIgnore
    Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sku", orphanRemoval = true)
    @JsonIgnore
    Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    @JsonBackReference
    Product product;

    @PrePersist
    protected void onCreate() {
        this.status = SKUStatus.PENDING;
        this.isDefault = false;
    }
}
