package com.masteryhub.e_commerce.repository;

import com.masteryhub.e_commerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}