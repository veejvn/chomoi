package com.ecommerce.chomoi.dto.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttributeOptionAddRequest {
    @NotBlank(message = "Value is required")
    private String value;
}
