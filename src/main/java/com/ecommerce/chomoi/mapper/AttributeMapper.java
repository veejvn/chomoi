package com.ecommerce.chomoi.mapper;


import com.ecommerce.chomoi.dto.attribute.AttributeAddRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeUpdateRequest;
import com.ecommerce.chomoi.dto.caterogy.AttributeResponse;
import com.ecommerce.chomoi.entities.Attribute;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeResponse toAttributeResponse(Attribute attribute);

    Attribute toAttribute(AttributeAddRequest request);

    void updateAttribute(AttributeUpdateRequest request, @MappingTarget Attribute attribute);

    List<AttributeResponse> toListAttributeResponse(List<Attribute> attributes);
}
