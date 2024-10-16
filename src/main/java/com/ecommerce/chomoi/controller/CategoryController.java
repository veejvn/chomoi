package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.attribute.AttributeAddRequest;
import com.ecommerce.chomoi.dto.caterogy.AttributeResponse;
import com.ecommerce.chomoi.dto.caterogy.CategoryAddRequest;
import com.ecommerce.chomoi.dto.caterogy.CategoryTreeNode;
import com.ecommerce.chomoi.dto.caterogy.CategoryUpdateRequest;
import com.ecommerce.chomoi.entities.Category;
import com.ecommerce.chomoi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody @Valid CategoryAddRequest request) {
        Category category = categoryService.add(request);
        ApiResponse<Category> response = ApiResponse.<Category>builder()
                .message("Add category successfully")
                .code("category-s-01")
                .data(category)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SHOP', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<CategoryTreeNode>>> getCategoryTree() {
        List<CategoryTreeNode> categoryTree = categoryService.getTree();
        ApiResponse<List<CategoryTreeNode>> response = ApiResponse.<List<CategoryTreeNode>>builder()
                .message("Fetch category tree successfully")
                .code("category-s-02")
                .data(categoryTree)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategoryName(
            @PathVariable String id,
            @RequestBody @Valid CategoryUpdateRequest request) {
        Category updatedCategory = categoryService.updateName(id, request);
        ApiResponse<Category> response = ApiResponse.<Category>builder()
                .message("Update category name successfully")
                .code("category-s-03")
                .data(updatedCategory)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable String id) {
        categoryService.delete(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Delete category successfully")
                .code("category-s-04")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}/attributes")
    @PreAuthorize("hasAnyRole('SHOP', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<AttributeResponse>>> getAttributes(@PathVariable String categoryId) {
        ApiResponse<List<AttributeResponse>> response = ApiResponse.<List<AttributeResponse>>builder()
                .message("Attribute got successfully")
                .code("category-s-05")
                .data(categoryService.getAttributesByCategoryId(categoryId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{categoryId}/attributes")
    public ResponseEntity<ApiResponse<AttributeResponse>> addAttributeToCategory(@PathVariable String categoryId, @RequestBody AttributeAddRequest request) {
        ApiResponse<AttributeResponse> response = ApiResponse.<AttributeResponse>builder()
                .message("Attribute added successfully")
                .code("category-s-06")
                .data(categoryService.addAttributeToCategory(categoryId, request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
