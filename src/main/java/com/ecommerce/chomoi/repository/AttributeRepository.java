package com.ecommerce.chomoi.repository;


import com.ecommerce.chomoi.entities.Attribute;
import com.ecommerce.chomoi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AttributeRepository extends JpaRepository<Attribute, String> {
    @Query("SELECT DISTINCT a FROM Attribute a LEFT JOIN FETCH a.options WHERE a.category = :category")
    List<Attribute> findByCategoryWithOptions(@Param("category") Category category);
}
