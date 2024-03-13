package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findAllByProduct(Product product);
}
