package com.ecommerce.chomoi.dto.attribute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeUpdateRequest {
    @NotBlank(message = "Name is required")
    String name;
    @NotNull(message = "IsEnterByHand is required")
    Boolean isEnterByHand;
    @NotNull(message = "Required is required")
    Boolean required;
}
