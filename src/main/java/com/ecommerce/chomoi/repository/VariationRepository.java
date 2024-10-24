package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepository extends JpaRepository<Variation, String> {
}
