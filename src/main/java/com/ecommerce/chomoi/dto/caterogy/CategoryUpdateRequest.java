package com.ecommerce.chomoi.dto.caterogy;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryUpdateRequest {
    @NotBlank(message = "Category name is required")
    private String name;
}
