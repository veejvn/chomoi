package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rvw_id")
    String id;

    @Column(name = "rvw_rating")
    String rating;

    @Column(name = "rvw_comment")
    String comment;

    @Column(name = "rvw_response")
    String response;

    @Column(name = "rvw_image")
    String image;

    @Column(name = "rvw_video")
    String video;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ReviewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    @JsonIgnore
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    @JsonIgnore
    SKU sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odr_id")
    @JsonIgnore
    Order order;

    @PrePersist
    protected void onCreate() {
        this.status = ReviewStatus.PENDING;
    }
}
