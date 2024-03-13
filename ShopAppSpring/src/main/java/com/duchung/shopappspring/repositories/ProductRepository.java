package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String productName);
}
