package com.ecommerce.chomoi.mapper;


import com.ecommerce.chomoi.dto.caterogy.AttributeResponse;
import com.ecommerce.chomoi.entities.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeResponse toAttributeResponse(Attribute attribute);

    List<AttributeResponse> toListAttributeResponse(List<Attribute> attributes);
}
