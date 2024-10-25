package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.ShopStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shp_id")
    String id;

    @Column(name = "shp_name", nullable = false)
    String name;

    @Column(name = "shp_avatar")
    String avatar;

    @Column(name = "shp_cover_image")
    String coverImage;

    @Column(name = "shp_rating")
    Double rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ShopStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_id")
    @JsonIgnore
    Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "adr_id")
    @JsonIgnore
    Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Product> products = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Order> orders = new HashSet<>();

    @PrePersist
    void onCreate() {
        this.status = ShopStatus.ACTIVE;
    }

}
