package com.ecommerce.chomoi.dto.caterogy;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryTreeNode {
    private String id;
    private String name;
    private String parentId;
    private Integer left;
    private Integer right;
    private Integer level;
    private Boolean isLeaf;
    private List<CategoryTreeNode> children = new ArrayList<>();
}
