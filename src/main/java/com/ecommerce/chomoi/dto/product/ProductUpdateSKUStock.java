package com.ecommerce.chomoi.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductUpdateSKUStock {
    @NotNull
    Integer quantity;
}
