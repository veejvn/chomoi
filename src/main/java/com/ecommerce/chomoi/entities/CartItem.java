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
public class CartItem {
    @Column(name = "crt_itm_quantity")
    String quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crt_id")
    Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    SKU sku;
}
