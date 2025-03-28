package com.ecommerce.chomoi.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductUpdateRequest {


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

    @NotEmpty(message = "At least one image path is required")
    @Size(min = 2, message = "Min 2")
    List<ImageDTO> images;

    @NotEmpty(message = "Product attributes cannot be empty")
    List<ProductAttributeDTO> productAttributes;

    @Data
    public static class ImageDTO {
        @NotBlank(message = "Image ID is required")
        String id;

        String path;
    }

    @Data
    public static class ProductAttributeDTO {
        @NotBlank(message = "Attribute ID is required")
        String attributeId;

        String value;
    }
}
