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

    @EmbeddedId
    ProductAttributeId id;

    @Column(name = "value", nullable = false)
    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "prd_id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("attributeId")
    @JoinColumn(name = "att_id")
    Attribute attribute;
}
