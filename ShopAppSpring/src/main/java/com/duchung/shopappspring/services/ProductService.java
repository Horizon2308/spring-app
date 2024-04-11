package com.duchung.shopappspring.services;

import com.duchung.shopappspring.domains.IsActive;
import com.duchung.shopappspring.dtos.ProductDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Category;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.repositories.CategoryRepository;
import com.duchung.shopappspring.repositories.ProductRepository;
import com.duchung.shopappspring.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ProductResponse> getAllProducts(Long categoryId, String keyword, Pageable pageable) {
        return productRepository.searchProducts(categoryId, keyword, pageable)
                .map(this::convertToProductResponse);
    }
    @Override
    public ProductResponse createProduct(ProductDTO productDTO) throws Exception {
        if (existedByProductName(productDTO.getName())) {
            throw new DataExistedException("Product existed");
        }
        // convert productDTO to product entity to save product
        Product newProduct =  convertToProduct(productDTO,
                categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category not found!")));
        // convert product to product response to send to client
        productRepository.save(newProduct);
        return convertToProductResponse(newProduct);
    }

    @Override
    public ProductResponse getProductById(Long productId) throws DataNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        return ProductResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .name(product.getName())
                .isActive(IsActive.ENABLE)
                .productImages(product.getProductImages())
                .build();
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductDTO productDTO) throws DataNotFoundException {
        Product updatedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setQuantity(productDTO.getQuantity());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setThumbnail(productDTO.getThumbnail());
        updatedProduct.setCategory(categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found!")));
        Product product = productRepository.save(updatedProduct);
        return convertToProductResponse(product);
    }

    @Override
    public void deleteProduct(Long productId) throws DataNotFoundException {
        Product deletedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        deletedProduct.setIsActive(IsActive.DISABLE);
    }

    @Override
    public boolean existedByProductName(String productName) {
        return productRepository.existsProductByName(productName);
    }

    private Product convertToProduct(ProductDTO productDTO, Category category) {
        return Product.builder()
                .name(productDTO.getName())
                .thumbnail(productDTO.getThumbnail())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(category)
                .quantity(productDTO.getQuantity())
                .isActive(IsActive.ENABLE)
                .build();
    }

    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .price(product.getPrice())
                .name(product.getName())
                .quantity(product.getQuantity())
                .isActive(IsActive.ENABLE)
                .build();
        productResponse.setCreateAt(product.getCreateAt());
        productResponse.setUpdateAt(product.getUpdateAt());
        return productResponse;
    }

}
