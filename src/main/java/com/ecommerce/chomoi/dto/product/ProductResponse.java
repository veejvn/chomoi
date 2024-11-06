package com.ecommerce.chomoi.dto.product;

import com.ecommerce.chomoi.entities.*;
import com.ecommerce.chomoi.entities.embeddedIds.ProductAttributeId;
import com.ecommerce.chomoi.enums.ProductStatus;
import com.ecommerce.chomoi.enums.ShopStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponse {
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
    ShopResponse shop;

    @Data
    public static class ProductAttributeDTO {
        ProductAttributeId id;
        Attribute attribute;
        String value;
    }

    @Data
    public static class ShopResponse {
        String id;

        String name;

        String avatar;

        String coverImage;

        Double rating;

        ShopStatus status;
    }

}
