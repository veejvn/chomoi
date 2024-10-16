package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.AttributeOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AttributeOptionRepository extends JpaRepository<AttributeOption, String> {
}
