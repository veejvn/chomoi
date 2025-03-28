package com.ecommerce.chomoi.dto.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttributeUpdateFieldRequest {
    @NotBlank(message = "Field name is required")
    private String field;

    @NotBlank(message = "Value is required")
    private String value;
}
