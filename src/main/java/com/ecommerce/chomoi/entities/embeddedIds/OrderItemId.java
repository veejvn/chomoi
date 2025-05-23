package com.ecommerce.chomoi.entities.embeddedIds;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemId {
    @Column(name = "ord_itm_ord_id")
    String orderId;

    @Column(name = "ord_itm_sku_id")
    String skuId;
}
