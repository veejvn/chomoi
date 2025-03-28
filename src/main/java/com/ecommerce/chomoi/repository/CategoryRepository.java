package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Modifying
    @Query("UPDATE Category c SET c.right = c.right + 2 WHERE c.right >= :right")
    void updateRightValues(@Param("right") int right);

    @Modifying
    @Query("UPDATE Category c SET c.left = c.left + 2 WHERE c.left > :right")
    void updateLeftValues(@Param("right") int right);


    @Query("SELECT MAX(c.right) FROM Category c")
    Integer findMaxRight();

    @Query("SELECT c FROM Category c ORDER BY c.left ASC")
    List<Category> findAllByOrderByLeftAsc();

    @Query("SELECT c FROM Category c WHERE c.left > :left AND c.right < :right")
    List<Category> findAllByLeftBetween(@Param("left") int left, @Param("right") int right);

    @Modifying
    @Query("UPDATE Category c SET c.left = c.left - :size WHERE c.left > :left")
    void updateLeftValuesAfterDelete(@Param("left") int left, @Param("size") int size);

    @Modifying
    @Query("UPDATE Category c SET c.right = c.right - :size WHERE c.right > :right")
    void updateRightValuesAfterDelete(@Param("right") int right, @Param("size") int size);

    boolean existsByParentId(String parentId);
}
