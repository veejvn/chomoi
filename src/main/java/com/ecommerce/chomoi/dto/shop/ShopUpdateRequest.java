package com.ecommerce.chomoi.dto.shop;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopUpdateRequest {
    @NotBlank(message = "Name not blank")
    String name;
    @NotBlank(message = "Avatar not blank")
    String avatar;
    @NotBlank(message = "CoverImage not blank")
    String coverImage;
}
