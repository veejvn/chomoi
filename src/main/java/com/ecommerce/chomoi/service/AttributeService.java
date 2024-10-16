package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.attribute.AttributeOptionAddRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeOptionUpdateRequest;
import com.ecommerce.chomoi.dto.caterogy.AttributeResponse;
import com.ecommerce.chomoi.entities.Attribute;
import com.ecommerce.chomoi.entities.AttributeOption;
import com.ecommerce.chomoi.entities.Category;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.AttributeMapper;
import com.ecommerce.chomoi.repository.AttributeOptionRepository;
import com.ecommerce.chomoi.repository.AttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeService {
    AttributeRepository attributeRepository;
    AttributeMapper attributeMapper;
    AttributeOptionRepository attributeOptionRepository;

    public Attribute createDefault(Category category, String name) {
        Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setCategory(category);
        attributeRepository.save(attribute);
        return attribute;
    }

    public List<Attribute> getByCategory(Category category) {
        return attributeRepository.findByCategoryWithOptions(category);
    }

    public void delete(String attributeId) {
        Attribute attribute = attributeRepository.findById(attributeId).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Attribute not found", "attribute-e-01")
        );
        attributeRepository.delete(attribute);
    }

    public AttributeResponse updateAttributeField(String attributeId, String field, String value) {
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute not found", "attribute-e-01"));
        switch (field) {
            case "name":
                attribute.setName(value);
                break;
            case "isEnterByHand":
                attribute.setIsEnterByHand(Boolean.parseBoolean(value));
                break;
            case "required":
                attribute.setRequired(Boolean.parseBoolean(value));
                break;
            default:
                throw new AppException(HttpStatus.BAD_REQUEST, "Invalid field name", "attribute-e-02");
        }
        Attribute updatedAttribute = attributeRepository.save(attribute);
        return attributeMapper.toAttributeResponse(updatedAttribute);
    }

    public AttributeOption addOptionToAttribute(String attributeId, AttributeOptionAddRequest request) {
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute not found", "attribute-e-01"));
        AttributeOption attributeOption = new AttributeOption();
        attributeOption.setValue(request.getValue());
        attributeOption.setAttribute(attribute);
        return attributeOptionRepository.save(attributeOption);
    }

    public AttributeOption updateOption(String optionId, AttributeOptionUpdateRequest request) {
        AttributeOption attributeOption = attributeOptionRepository
                .findById(optionId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute option not found", "attribute-option-e-01"));
        attributeOption.setValue(request.getValue());
        return attributeOptionRepository.save(attributeOption);
    }

    public void deleteOption(String optionId) {
        AttributeOption attributeOption = attributeOptionRepository
                .findById(optionId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute option not found", "attribute-option-e-01"));
        attributeOptionRepository.delete(attributeOption);
    }
}
