package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.product.*;
import com.ecommerce.chomoi.entities.Product;
import com.ecommerce.chomoi.entities.ProductAttribute;
import com.ecommerce.chomoi.entities.SKU;
import com.ecommerce.chomoi.entities.VariationOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true), // Bỏ qua ID, để nó tự động tạo
            @Mapping(target = "status", ignore = true), // Bỏ qua status, nó sẽ được gán trong @PrePersist
            @Mapping(target = "rating", ignore = true), // Bỏ qua rating, gán giá trị mặc định trong @PrePersist
            @Mapping(target = "sold", ignore = true), // Bỏ qua sold, gán giá trị mặc định trong @PrePersist
            @Mapping(target = "variations", source = "variations"),
            @Mapping(target = "images", ignore = true), // Bỏ qua images nếu bạn không ánh xạ ở đây
            @Mapping(target = "skus", ignore = true), // Bỏ qua skus nếu bạn không ánh xạ ở đây
            @Mapping(target = "reviews", ignore = true), // Bỏ qua reviews nếu bạn không ánh xạ ở đây
            @Mapping(target = "productAttributes", ignore = true) // Bỏ qua productAttributes nếu bạn không ánh xạ ở đây
    })
    Product toEntity(ProductAddRequest productAddRequest);

    SKU toSKU(ProductAddRequest.SKURequestDTO skuRequestDTO);

    default List<VariationOption> mapOptions(List<String> options) {
        if (options == null) {
            return new ArrayList<>();
        }
        return options.stream()
                .map(optionValue -> VariationOption.builder()
                        .value(optionValue)
                        .build())
                .toList();
    }

    ProductForShopResponse toProductForShopResponse(Product product);

    List<ProductForShopResponse> toProductListForShopResponse(List<Product> productList);

    ProductForShopResponse.ProductAttributeDTO toProductAttributeDTO(ProductAttribute productAttribute);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "productAttributes", ignore = true)
    void updateEntityFromDto(ProductUpdateRequest request, @MappingTarget Product product);

    @Mappings({
            @Mapping(target = "minPrice", expression = "java(getMinPrice(product))"),
            @Mapping(target = "maxPrice", expression = "java(getMaxPrice(product))")
    })
    ProductTagResponse toProductTagResponse(Product product);

    default Page<ProductTagResponse> toPageProductTagResponse(Page<Product> productPage) {
        List<ProductTagResponse> productTagResponses = productPage.getContent().stream()
                .map(this::toProductTagResponse)
                .toList();
        return new PageImpl<>(productTagResponses, productPage.getPageable(), productPage.getTotalElements());
    }

    default BigDecimal getMinPrice(Product product) {
        return product.getSkus().stream()
                .map(SKU::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    default BigDecimal getMaxPrice(Product product) {
        return product.getSkus().stream()
                .map(SKU::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> productList);
}
