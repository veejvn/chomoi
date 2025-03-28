package com.ecommerce.chomoi.dto.order;

import com.ecommerce.chomoi.entities.embeddedIds.CartItemId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    String note;

    @NotBlank(message = "Address is required")
    String addressId;

    @NotEmpty(message = "At least one order item is required")
    @Size(min = 1, message = "Min 1")
    List<@Valid OrderItemDTO> items;

    @NotBlank(message = "Shop is required")
    String shopId;

    @Data
    public static class OrderItemDTO {

        @NotNull(message = "CartItemId is required")
        CartItemId cartItemId;
    }
}
