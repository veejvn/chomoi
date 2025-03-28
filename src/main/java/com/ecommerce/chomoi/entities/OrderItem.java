package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.entities.embeddedIds.OrderItemId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderItem {

    @EmbeddedId
    OrderItemId id;

    @Column(name = "ord_itm_quantity")
    int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id")
    @JsonBackReference
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    SKU sku;
}
