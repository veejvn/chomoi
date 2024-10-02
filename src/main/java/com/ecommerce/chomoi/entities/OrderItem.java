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
public class OrderItem {

    @EmbeddedId
    OrderItemId id;

    @Column(name = "ord_itm_quantity")
    String quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "ord_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skuId")
    @JoinColumn(name = "sku_id")
    SKU sku;
}
