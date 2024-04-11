package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.ProductDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<ProductResponse> getAllProducts(Long categoryId, String keyword, Pageable pageable);
    ProductResponse createProduct(ProductDTO productDTO) throws Exception;
    ProductResponse getProductById(Long productId) throws DataNotFoundException;
    ProductResponse updateProduct(Long productId, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long productId) throws DataNotFoundException;
    boolean existedByProductName(String productName);
}
