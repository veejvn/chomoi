package com.ecommerce.chomoi.controller;


import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.attribute.AttributeOptionAddRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeOptionUpdateRequest;
import com.ecommerce.chomoi.dto.attribute.AttributeUpdateRequest;
import com.ecommerce.chomoi.dto.caterogy.AttributeResponse;
import com.ecommerce.chomoi.entities.AttributeOption;
import com.ecommerce.chomoi.service.AttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AttributeController {
    private final AttributeService attributeService;

    @DeleteMapping("/{attributeId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String attributeId) {
        attributeService.delete(attributeId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Attribute deleted successfully")
                .code("attribute-s-02")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{attributeId}")
    public ResponseEntity<ApiResponse<AttributeResponse>> update(
            @PathVariable String attributeId,
            @RequestBody @Valid AttributeUpdateRequest request) {
        AttributeResponse updatedAttribute = attributeService.update(attributeId, request);
        ApiResponse<AttributeResponse> response = ApiResponse.<AttributeResponse>builder()
                .message("Attribute field updated successfully")
                .code("attribute-s-03")
                .data(updatedAttribute)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attributeId}/options")
    public ResponseEntity<ApiResponse<AttributeOption>> addOptionToAttribute(
            @PathVariable String attributeId, @RequestBody @Valid AttributeOptionAddRequest request) {
        ApiResponse<AttributeOption> response = ApiResponse.<AttributeOption>builder()
                .message("Option added successfully")
                .code("attribute-s-04")
                .data(attributeService.addOptionToAttribute(attributeId, request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/options/{optionId}")
    public ResponseEntity<ApiResponse<AttributeOption>> updateAttributeOption(
            @PathVariable String optionId,
            @RequestBody @Valid AttributeOptionUpdateRequest request) {
        AttributeOption updatedAttribute = attributeService.updateOption(optionId, request);
        ApiResponse<AttributeOption> response = ApiResponse.<AttributeOption>builder()
                .message("Option updated successfully")
                .code("attribute-option-s-01")
                .data(updatedAttribute)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeOption(
            @PathVariable String optionId) {
        attributeService.deleteOption(optionId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Option deleted successfully")
                .code("attribute-option-s-02")
                .build();
        return ResponseEntity.ok(response);
    }
}
