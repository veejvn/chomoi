package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.attribute.AttributeAddRequest;
import com.ecommerce.chomoi.dto.caterogy.AttributeResponse;
import com.ecommerce.chomoi.dto.caterogy.CategoryAddRequest;
import com.ecommerce.chomoi.dto.caterogy.CategoryTreeNode;
import com.ecommerce.chomoi.dto.caterogy.CategoryUpdateRequest;
import com.ecommerce.chomoi.entities.Attribute;
import com.ecommerce.chomoi.entities.Category;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.AttributeMapper;
import com.ecommerce.chomoi.mapper.CategoryMapper;
import com.ecommerce.chomoi.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    AttributeService attributeService;
    AttributeMapper attributeMapper;

    public Category add(CategoryAddRequest request) {
        Category category = categoryMapper.toCategory(request);
        if (category.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(category.getParentId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Parent category not found", "category-e-01"));
            int parentRight = parentCategory.getRight();
            categoryRepository.updateRightValues(parentRight);
            categoryRepository.updateLeftValues(parentRight);
            category.setLeft(parentRight);
            category.setRight(parentRight + 1);
            category.setLevel(parentCategory.getLevel() + 1);
            category.setIsLeaf(true);
            parentCategory.setIsLeaf(false);
            parentCategory.setRight(parentCategory.getRight() + 2);
            categoryRepository.save(parentCategory);
        } else {
            Integer maxRight = categoryRepository.findMaxRight();
            int newRight = (maxRight != null) ? maxRight + 1 : 1;
            category.setLeft(newRight);
            category.setRight(newRight + 1);
            category.setLevel(1);
            category.setIsLeaf(true);
        }
        return categoryRepository.save(category);
    }

    public List<CategoryTreeNode> buildTree(List<Category> categories) {
        categories.sort(Comparator.comparingInt(Category::getLeft));
        Map<String, CategoryTreeNode> categoryMap = new HashMap<>();
        List<CategoryTreeNode> tree = new ArrayList<>();
        for (Category category : categories) {
            CategoryTreeNode node = categoryMapper.toCategoryTreeNode(category);
            categoryMap.put(category.getId(), node);
        }
        for (CategoryTreeNode node : categoryMap.values()) {
            if (node.getParentId() == null) {
                tree.add(node);
            } else {
                CategoryTreeNode parentNode = categoryMap.get(node.getParentId());
                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                }
            }
        }
        sortTreeByLeft(tree);
        return tree;
    }

    private void sortTreeByLeft(List<CategoryTreeNode> nodes) {
        nodes.sort(Comparator.comparingInt(CategoryTreeNode::getLeft));
        for (CategoryTreeNode node : nodes) {
            if (!node.getChildren().isEmpty()) {
                sortTreeByLeft(node.getChildren());
            }
        }
    }

    public List<CategoryTreeNode> getTree() {
        List<Category> categories = categoryRepository.findAllByOrderByLeftAsc();
        return buildTree(categories);
    }

    public Category updateName(String id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Category not found", "category-e-01"));
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    public void delete(String id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Category not found", "category-e-02"));

        int leftValue = categoryToDelete.getLeft();
        int rightValue = categoryToDelete.getRight();
        int size = rightValue - leftValue + 1;

        List<Category> childCategories = categoryRepository.findAllByLeftBetween(leftValue, rightValue);
        categoryRepository.deleteAll(childCategories);

        categoryRepository.updateLeftValuesAfterDelete(leftValue, size);
        categoryRepository.updateRightValuesAfterDelete(rightValue, size);

        if (categoryToDelete.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryToDelete.getParentId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Parent category not found", "category-e-03"));
            boolean hasChildren = categoryRepository.existsByParentId(parentCategory.getId());
            if (!hasChildren) {
                parentCategory.setIsLeaf(true);
                categoryRepository.save(parentCategory);
            }
        }
        categoryRepository.delete(categoryToDelete);
    }

    public List<AttributeResponse> getAttributesByCategoryId(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Category not found", "category-e-01"));
        List<Attribute> attributes = attributeService.getByCategory(category);
        return attributeMapper.toListAttributeResponse(attributes);
    }


    public AttributeResponse addAttributeToCategory(String categoryId, AttributeAddRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Category not found", "category-e-01"));
        if (!category.getIsLeaf()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Category is not leaf", "category-e-02");
        }
        Attribute attribute = attributeService.createDefault(category, request);
        return attributeMapper.toAttributeResponse(attribute);
    }
}
