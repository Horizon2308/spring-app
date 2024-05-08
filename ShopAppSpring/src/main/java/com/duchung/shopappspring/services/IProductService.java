package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.ProductDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.responses.ProductResponse;
import com.duchung.shopappspring.responses.ProductWithoutCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<ProductResponse> getAllProducts(Long categoryId, String keyword, Pageable pageable);
    ProductResponse createProduct(ProductDTO productDTO) throws Exception;
    ProductWithoutCategoryResponse getProductById(Long productId) throws DataNotFoundException;
    ProductResponse updateProduct(Long productId, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long productId) throws DataNotFoundException;
    boolean existedByProductName(String productName);
    List<Product> findProductsByIds(List<Long> ids);
}
