package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "prd_id")
    String id;

    @Column(name = "prd_name", nullable = false)
    String name;

    @Column(name = "prd_description", columnDefinition = "TEXT", nullable = false)
    @Size(min = 1000, max = 10000)
    String description;

    @Column(name = "prd_rating")
    Double rating;

    @Column(name = "prd_sold")
    Integer sold;

    @Column(name = "prd_thumbnail", nullable = false)
    String thumbnail;

    @Column(name = "prd_video")
    String video;

    @Column(name = "prd_slug", nullable = true)
    String slug;

    @Column(name = "prd_is_simple", nullable = false)
    Boolean isSimple = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "prd_status", nullable = false)
    ProductStatus status;

    @Column(name = "prd_created_at")
    LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    List<Variation> variations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    List<Image> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    List<SKU> skus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    List<Review> reviews = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    List<ProductAttribute> productAttributes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctg_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shp_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    Shop shop;


    @PrePersist
    void onCreate() {
        this.rating = 0.0;
        this.sold = 0;
        this.createdAt = LocalDateTime.now();
    }
}
