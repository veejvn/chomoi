package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.entities.embeddedIds.ProductAttributeId;
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
public class ProductAttribute {

    @EmbeddedId
    ProductAttributeId id;

    @Column(name = "prd_itm_value", nullable = false)
    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    @JsonIgnore
    Product product;

    @ManyToOne
    @JoinColumn(name = "att_id")
    Attribute attribute;
}
