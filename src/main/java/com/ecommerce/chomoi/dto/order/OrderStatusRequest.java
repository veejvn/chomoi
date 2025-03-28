package com.ecommerce.chomoi.dto.order;

import com.ecommerce.chomoi.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatusRequest {

    @NotNull(message = "Status is required")
    OrderStatus status;
}
