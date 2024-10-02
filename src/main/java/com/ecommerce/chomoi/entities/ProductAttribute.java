package com.ecommerce.chomoi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ProductAttribute {
    @Column(name = "value", nullable = false)
    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "att_id")
    Attribute attribute;
}
