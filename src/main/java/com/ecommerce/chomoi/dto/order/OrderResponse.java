package com.ecommerce.chomoi.dto.order;

import com.ecommerce.chomoi.dto.account.AccountResponse;
import com.ecommerce.chomoi.dto.address.AddressResponse;
import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.entities.SKU;
import com.ecommerce.chomoi.entities.Variation;
import com.ecommerce.chomoi.entities.embeddedIds.OrderItemId;
import com.ecommerce.chomoi.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    int totalQuantity;
    BigDecimal totalPrice;
    OrderStatus status;
    String note;
    AddressResponse address;
    List<OrderItemDTO> orderItems;
    AccountResponse buyer;
    ShopResponse shop;

    @Data
    public static class OrderItemDTO {

        OrderItemId id;

        int quantity;

        SKU sku;

        OrderItemProductResponseDTO product;

        @Data
        public static class OrderItemProductResponseDTO {
            String id;
            String name;
            String thumbnail;
            String slug;
            Boolean isSimple;
            List<Variation> variations;
        }
    }
}
