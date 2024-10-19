package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.attribute.AttributeAddRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeOptionAddRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeOptionUpdateRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeUpdateRequest;
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

    public Attribute createDefault(Category category, AttributeAddRequest request) {
        Attribute attribute = attributeMapper.toAttribute(request);
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

    public AttributeResponse update(String attributeId, AttributeUpdateRequest request) {
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Attribute not found", "attribute-e-01"));
        attributeMapper.updateAttribute(request, attribute);
        attributeRepository.save(attribute);
        return attributeMapper.toAttributeResponse(attribute);
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
