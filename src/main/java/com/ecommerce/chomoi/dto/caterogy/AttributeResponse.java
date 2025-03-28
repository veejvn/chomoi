package com.ecommerce.chomoi.dto.caterogy;

import com.ecommerce.chomoi.entities.AttributeOption;
import lombok.Data;

import java.util.List;

@Data
public class AttributeResponse {
    String id;
    String name;
    Boolean isEnterByHand;
    Boolean required;
    List<AttributeOption> options;
}
