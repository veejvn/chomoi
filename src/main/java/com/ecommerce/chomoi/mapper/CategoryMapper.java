package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.caterogy.CategoryAddRequest;
import com.ecommerce.chomoi.dto.caterogy.CategoryTreeNode;
import com.ecommerce.chomoi.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryAddRequest request);

    CategoryTreeNode toCategoryTreeNode(Category category);
}
