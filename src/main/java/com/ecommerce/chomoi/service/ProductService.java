package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.product.*;
import com.ecommerce.chomoi.entities.*;
import com.ecommerce.chomoi.entities.embeddedIds.ProductAttributeId;
import com.ecommerce.chomoi.enums.ProductStatus;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.ProductMapper;
import com.ecommerce.chomoi.repository.*;
import com.ecommerce.chomoi.security.SecurityUtil;
import com.ecommerce.chomoi.util.CommonUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    VariationRepository variationRepository;
    AttributeRepository attributeRepository;
    VariationOptionRepository variationOptionRepository;
    ProductMapper productMapper;
    SecurityUtil securityUtil;
    UploadService uploadService;

    @Transactional
    public ProductForShopResponse addProduct(ProductAddRequest productAddRequest) {
        if (productAddRequest.getStatus() != ProductStatus.DRAFT && productAddRequest.getStatus() != ProductStatus.PENDING) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid product status. Only DRAFT and PENDING are allowed", "product-e-03");
        }
        Shop shop = securityUtil.getShop();
        Category category = categoryRepository
                .findById(productAddRequest.getCategoryId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Category not found", "category-e-01"));
        Product product = productMapper.toEntity(productAddRequest);
        product.setStatus(productAddRequest.getStatus());
        product.setShop(shop);
        product.setCategory(category);
        String slug = CommonUtil.toProductSlug(product.getName());
        product.setSlug(slug);

        if (Boolean.TRUE.equals(productAddRequest.getIsSimple())) {
            SKU sku = createSimpleProductSKU(productAddRequest, product);
            List<SKU> skus = new ArrayList<>();
            skus.add(sku);
            product.setSkus(skus);

        } else {
            if (productAddRequest.getVariations() == null || productAddRequest.getVariations().isEmpty()) {
                throw new AppException(HttpStatus.BAD_REQUEST, "Variations cannot be null or empty for complex products", "product-e-02");
            }
            List<Variation> variations = createVariations(productAddRequest, product);
            List<SKU> skus = createSkus(productAddRequest, product, variations);
            product.setVariations(variations);
            product.setSkus(skus);
        }
        List<Image> images = new ArrayList<>();
        for (String imagePath : productAddRequest.getImagePaths()) {
            Image image = Image.builder().path(imagePath).product(product).build();
            images.add(image);
        }
        product.setImages(images);

        productRepository.save(product);

        List<ProductAttribute> productAttributes = createProductAttributes(productAddRequest, product);
        product.setProductAttributes(productAttributes);

        productRepository.save(product);
        return this.getByShopAndId(product.getId());
    }

    private SKU createSimpleProductSKU(ProductAddRequest productAddRequest, Product product) {
        ProductAddRequest.SKURequestDTO skuRequestDTO = productAddRequest.getSkus().get(0);
        SKU sku = productMapper.toSKU(skuRequestDTO);
        sku.setProduct(product);
        sku.setIsDefault(true);
        sku.setVariation("");
        return sku;
    }

    private List<Variation> createVariations(ProductAddRequest productAddRequest, Product product) {
        List<Variation> variations = new ArrayList<>();
        for (ProductAddRequest.VariationDTO variationDTO : productAddRequest.getVariations()) {
            Variation variation = Variation.builder()
                    .name(variationDTO.getName())
                    .product(product)
                    .build();
            List<VariationOption> variationOptions = new ArrayList<>();
            for (String optionValue : variationDTO.getOptions()) {
                VariationOption variationOption = VariationOption.builder()
                        .value(optionValue)
                        .variation(variation)
                        .build();
                variationOptions.add(variationOption);
            }
            variation.setOptions(variationOptions);
            variations.add(variation);
        }
        variationRepository.saveAll(variations);
        for (Variation variation : variations) {
            variationOptionRepository.saveAll(variation.getOptions());
        }
        return variations;
    }

    private List<SKU> createSkus(ProductAddRequest productAddRequest, Product product, List<Variation> variations) {
        List<SKU> skus = new ArrayList<>();
        for (ProductAddRequest.SKURequestDTO skuRequestDTO : productAddRequest.getSkus()) {
            SKU sku = productMapper.toSKU(skuRequestDTO);
            sku.setProduct(product);
            String variationOptionIds = getVariationOptionIds(skuRequestDTO.getVariation(), variations);
            sku.setVariation(variationOptionIds);
            skus.add(sku);
        }
        return skus;
    }

    private String getVariationOptionIds(String variationOrderString, List<Variation> variations) {
        String[] orders = variationOrderString.split(" ");
        StringBuilder variationOptionIds = new StringBuilder();
        for (int i = 0; i < orders.length; i++) {
            int variationOrder = Integer.parseInt(orders[i]);
            Variation variation = variations.get(i);
            VariationOption option = variation.getOptions().get(variationOrder);
            variationOptionIds.append(option.getId()).append(" ");
        }
        return variationOptionIds.toString().trim();
    }

    private List<ProductAttribute> createProductAttributes(ProductAddRequest productAddRequest, Product product) {
        List<ProductAttribute> productAttributes = new ArrayList<>();
        for (ProductAddRequest.ProductAttributeRequestDTO attributeRequestDTO : productAddRequest.getProductAttributes()) {
            Attribute attribute = attributeRepository
                    .findById(attributeRequestDTO.getAttributeId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute not found", "attribute-e-01"));

            ProductAttribute productAttribute = ProductAttribute.builder()
                    .id(new ProductAttributeId(product.getId(), attribute.getId()))
                    .product(product)
                    .attribute(attribute)
                    .value(attributeRequestDTO.getValue())
                    .build();

            productAttributes.add(productAttribute);
        }
        return productAttributes;
    }

    public ProductForShopResponse getByShopAndId(String productId) {
        Shop shop = securityUtil.getShop();
        Product product = productRepository.findByIdAndShop(productId, shop)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Product not found", "product-e-01"));
        return productMapper.toProductForShopResponse(product);
    }

    public List<ProductForShopResponse> getAllByShop() {
        Shop shop = securityUtil.getShop();
        List<Product> productList = productRepository.findAllByShop(shop);
        return productMapper.toProductListForShopResponse(productList);
    }

    @Transactional
    public ProductForShopResponse updateProduct(String productId, ProductUpdateRequest productUpdateRequest) {
        Shop shop = securityUtil.getShop();
        Product product = productRepository.findByIdAndShop(productId, shop)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Product not found", "product-e-01"));
        Category category = categoryRepository.findById(productUpdateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Category not found", "category-e-01"));
        productMapper.updateEntityFromDto(productUpdateRequest, product);
        product.setCategory(category);
        List<String> newImageIds = productUpdateRequest.getImages().stream()
                .map(ProductUpdateRequest.ImageDTO::getId)
                .filter(Objects::nonNull)
                .toList();
        List<Image> existingImages = product.getImages();
        List<Image> imagesToRemove = existingImages.stream()
                .filter(image -> !newImageIds.contains(image.getId()))
                .toList();
        for (Image imageToRemove : imagesToRemove) {
            try {
                uploadService.deleteFile(imageToRemove.getPath());
            } catch (IOException e) {
                log.info("Cannot delete image :: {}", imageToRemove.getPath());
            }
        }
        existingImages.removeAll(imagesToRemove);
        for (ProductUpdateRequest.ImageDTO imageDTO : productUpdateRequest.getImages()) {
            if (imageDTO.getId() != null) {
                Image image = existingImages.stream()
                        .filter(existingImage -> existingImage.getId().equals(imageDTO.getId()))
                        .findFirst()
                        .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Image not found", "image-e-01"));
                image.setPath(imageDTO.getPath());
            } else {
                Image newImage = Image.builder()
                        .path(imageDTO.getPath())
                        .product(product)
                        .build();
                existingImages.add(newImage);
            }
        }
        product.setImages(existingImages);
        List<String> newAttributeIds = productUpdateRequest.getProductAttributes().stream()
                .map(ProductUpdateRequest.ProductAttributeDTO::getAttributeId).toList();
        List<ProductAttribute> existingAttributes = product.getProductAttributes();
        existingAttributes.removeIf(attr -> !newAttributeIds.contains(attr.getAttribute().getId()));
        for (ProductUpdateRequest.ProductAttributeDTO attrDTO : productUpdateRequest.getProductAttributes()) {
            ProductAttribute productAttribute = existingAttributes.stream()
                    .filter(existingAttr -> existingAttr.getAttribute().getId().equals(attrDTO.getAttributeId()))
                    .findFirst()
                    .orElse(ProductAttribute.builder()
                            .id(new ProductAttributeId(product.getId(), attrDTO.getAttributeId()))
                            .product(product)
                            .attribute(attributeRepository.findById(attrDTO.getAttributeId())
                                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute not found", "attribute-e-01")))
                            .build());
            productAttribute.setValue(attrDTO.getValue());
            existingAttributes.add(productAttribute);
        }
        product.setProductAttributes(existingAttributes);
        if (product.getStatus() != ProductStatus.DRAFT) {
            product.setStatus(ProductStatus.PENDING);
        }
        productRepository.save(product);
        return this.getByShopAndId(product.getId());
    }

    public void shopChangeStatus(String productId, ProductStatus productStatusRequest) {
        Shop shop = securityUtil.getShop();
        Product product = productRepository.findByIdAndShop(productId, shop)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Product not found", "product-e-01"));
        ProductStatus currentStatus = product.getStatus();
        if (productStatusRequest == ProductStatus.DELETED) {
            product.setStatus(ProductStatus.DELETED);
            productRepository.save(product);
            return;
        }
        switch (currentStatus) {
            case DRAFT:
                if (productStatusRequest == ProductStatus.PENDING) {
                    product.setStatus(productStatusRequest);
                } else {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Cannot change status from DRAFT to " + productStatusRequest, "product-e-04");
                }
                break;
            case PENDING:
                if (productStatusRequest == ProductStatus.DRAFT) {
                    product.setStatus(productStatusRequest);
                } else {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Cannot change status from PENDING to " + productStatusRequest, "product-e-05");
                }
                break;
            case ACTIVE:
                if (productStatusRequest == ProductStatus.DRAFT) {
                    product.setStatus(productStatusRequest);
                } else {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Cannot change status from ACTIVE to " + productStatusRequest, "product-e-06");
                }
                break;
            default:
                throw new AppException(HttpStatus.BAD_REQUEST, "Invalid product status", "product-e-07");
        }
        productRepository.save(product);
    }

    public void adminChangeStatus(String productId, ProductStatus productStatusRequest) {
        Product product = productRepository.findByIdAndAmin(productId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Product not found", "product-e-01"));
        ProductStatus currentStatus = product.getStatus();

        switch (currentStatus) {
            case PENDING:
                if (productStatusRequest == ProductStatus.ACTIVE || productStatusRequest == ProductStatus.REJECTED) {
                    product.setStatus(productStatusRequest);
                } else {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Cannot change status from PENDING to " + productStatusRequest, "product-e-08");
                }
                break;
            case ACTIVE:
                if (productStatusRequest == ProductStatus.BLOCKED || productStatusRequest == ProductStatus.DELETED) {
                    product.setStatus(productStatusRequest);
                } else {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Cannot change status from ACTIVE to " + productStatusRequest, "product-e-09");
                }
                break;
            case BLOCKED:
                if (productStatusRequest == ProductStatus.DRAFT) {
                    product.setStatus(productStatusRequest);
                } else {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Cannot change status from BLOCKED to " + productStatusRequest, "product-e-10");
                }
                break;
            default:
                throw new AppException(HttpStatus.BAD_REQUEST, "Invalid product status", "product-e-07");
        }

        productRepository.save(product);
    }

    public Page<ProductTagResponse> getHome(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        Page<Product> productPage = productRepository.findForHome(pageable);
        return productMapper.toPageProductTagResponse(productPage);
    }

    public ProductResponse getByIdOrSlug(String productIdOrSlug) {
        Product product = productRepository.findByIdOrSlug(productIdOrSlug).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Product not found", "product-e-01")
        );
        return productMapper.toProductResponse(product);
    }

    public Page<ProductTagResponse> getAllByShopId(String idShop, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        Page<Product> productPage = productRepository.findByShopId(idShop, pageable);
        return productMapper.toPageProductTagResponse(productPage);

    }

    public Page<ProductTagResponse> adminGetDashboard(int page, int size, String statusRequest) {
        ProductStatus productStatus = ProductStatus.PENDING;
        try {
            productStatus = ProductStatus.valueOf(statusRequest.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(HttpStatus.CONFLICT, "Cannot convert to :: " + statusRequest);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.adminGetDashboard(productStatus, pageable);
        return productMapper.toPageProductTagResponse(productPage);
    }

    public Page<ProductTagResponse> search(int page, int size, String query) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.search(query, pageable);
        return productMapper.toPageProductTagResponse(productPage);
    }

}
