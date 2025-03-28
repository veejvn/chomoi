package com.ecommerce.chomoi.dto.product;

import com.ecommerce.chomoi.entities.Category;
import com.ecommerce.chomoi.entities.Image;
import com.ecommerce.chomoi.entities.SKU;
import com.ecommerce.chomoi.entities.Variation;
import com.ecommerce.chomoi.entities.embeddedIds.ProductAttributeId;
import com.ecommerce.chomoi.enums.ProductStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductForShopResponse {
    String id;
    String name;
    String description;
    Double rating;
    Integer sold;
    String thumbnail;
    String video;
    String slug;
    Boolean isSimple = true;
    ProductStatus status;
    List<Variation> variations = new ArrayList<>();
    List<Image> images = new ArrayList<>();
    List<SKU> skus = new ArrayList<>();
    List<ProductAttributeDTO> productAttributes = new ArrayList<>();
    Category category;

    @Data
    public static class ProductAttributeDTO {
        ProductAttributeId id;
        String value;
    }
}
