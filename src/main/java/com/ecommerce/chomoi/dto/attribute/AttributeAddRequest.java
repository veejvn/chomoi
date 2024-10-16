package com.ecommerce.chomoi.dto.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttributeAddRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
