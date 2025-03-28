package com.ecommerce.chomoi.repository;

import com.ecommerce.chomoi.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
