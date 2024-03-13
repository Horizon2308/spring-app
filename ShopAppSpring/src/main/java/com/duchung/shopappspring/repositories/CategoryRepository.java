package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoryByName(String categoryName);
}
