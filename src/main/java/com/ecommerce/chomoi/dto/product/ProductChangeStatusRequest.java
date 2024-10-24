package com.ecommerce.chomoi.dto.product;

import com.ecommerce.chomoi.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductChangeStatusRequest {
    @NotNull(message = "Status is required")
    ProductStatus status;
}
