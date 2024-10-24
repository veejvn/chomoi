package com.ecommerce.chomoi.dto.product;

import com.ecommerce.chomoi.enums.ProductStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAddRequest {

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 100, max = 10000, message = "Description must be between 10 and 1000 characters")
    String description;

    @NotBlank(message = "Thumbnail is required")
    String thumbnail;

    String video;

    @NotBlank(message = "Category ID is required")
    String categoryId;

    @NotNull(message = "isSimple is required")
    Boolean isSimple;

    @NotNull(message = "Status is required")
    ProductStatus status;

    @Size(max = 3, message = "Max 3")
    List<@Valid VariationDTO> variations;

    @NotNull(message = "SKUs is required")
    @Size(min = 1, message = "Min 1")
    List<@Valid SKURequestDTO> skus;

    @NotEmpty(message = "At least one image path is required")
    @Size(min = 2, message = "Min 2")
    List<@NotBlank(message = "Image path cannot be blank") String> imagePaths;

    @NotEmpty(message = "Product attributes cannot be empty")
    List<@Valid ProductAttributeRequestDTO> productAttributes;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VariationDTO {
        @NotBlank(message = "Name is required")
        String name;

        @NotEmpty(message = "Variation must have at least one option")
        List<@NotBlank(message = "Option value cannot be blank") String> options;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SKURequestDTO {
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive number")
        @Digits(integer = 10, fraction = 2, message = "Price must be a valid number with up to 2 decimal places")
        BigDecimal price;

        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock must be a non-negative number")
        Integer stock;

        @NotNull(message = "Weight is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be a positive number")
        @Digits(integer = 10, fraction = 2, message = "Weight must be a valid number with up to 2 decimal places")
        BigDecimal weight;

        @NotBlank(message = "Variation is required")
        String variation;

        @NotBlank(message = "Image is required")
        String image;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductAttributeRequestDTO {
        @NotBlank(message = "Attribute ID is required")
        String attributeId;

        @NotBlank(message = "Attribute value cannot be blank")
        String value;
    }
}
