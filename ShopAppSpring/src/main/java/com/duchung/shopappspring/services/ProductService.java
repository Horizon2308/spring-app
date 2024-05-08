package com.duchung.shopappspring.services;

import com.duchung.shopappspring.domains.IsActive;
import com.duchung.shopappspring.dtos.ProductDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Category;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.models.Provider;
import com.duchung.shopappspring.repositories.CategoryRepository;
import com.duchung.shopappspring.repositories.ProductRepository;
import com.duchung.shopappspring.repositories.ProviderRepository;
import com.duchung.shopappspring.responses.ProductResponse;
import com.duchung.shopappspring.responses.ProductWithoutCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;

    @Override
    public Page<ProductResponse> getAllProducts(Long categoryId, String keyword, Pageable pageable) {
        return productRepository.searchProducts(categoryId, keyword, pageable)
                .map(this::convertToProductResponse);
    }

    @Transactional
    @Override
    public ProductResponse createProduct(ProductDTO productDTO) throws Exception {
        if (existedByProductName(productDTO.getName())) {
            throw new DataExistedException("Product existed");
        }
        // convert productDTO to product entity to save product
        Product newProduct =  convertToProduct(productDTO,
                categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category not found!")),
                providerRepository.findById(productDTO.getProviderId())
                        .orElseThrow(() -> new DataNotFoundException("Provider not found!")));
        // convert product to product response to send to client
        productRepository.save(newProduct);
        return convertToProductResponse(newProduct);
    }

    @Override
    public ProductWithoutCategoryResponse getProductById(Long productId) throws DataNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        return ProductWithoutCategoryResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .quantity(product.getQuantity())
                .status(product.getStatus())
                .price(product.getPrice())
                .name(product.getName())
                .isActive(IsActive.ENABLE)
                .productImages(product.getProductImages())
                .build();
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long productId, ProductDTO productDTO) throws DataNotFoundException {
        Product updatedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setQuantity(productDTO.getQuantity());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setStatus(productDTO.getStatus());
        updatedProduct.setThumbnail(productDTO.getThumbnail());
        updatedProduct.setCategory(categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found!")));
        Product product = productRepository.save(updatedProduct);
        return convertToProductResponse(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) throws DataNotFoundException {
        Product deletedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        deletedProduct.setActive(IsActive.DISABLE);
    }

    @Override
    public boolean existedByProductName(String productName) {
        return productRepository.existsProductByName(productName);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> ids) {
        return productRepository.findProductsByIds(ids);
    }

    private Product convertToProduct(ProductDTO productDTO, Category category, Provider provider) {
        return Product.builder()
                .name(productDTO.getName())
                .thumbnail(productDTO.getThumbnail())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(category)
                .quantity(productDTO.getQuantity())
                .status(productDTO.getStatus())
                .provider(provider)
                .active(IsActive.ENABLE)
                .build();
    }

    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .category(product.getCategory())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .price(product.getPrice())
                .name(product.getName())
                .quantity(product.getQuantity())
                .status(product.getStatus())
                .isActive(IsActive.ENABLE)
                .build();
        productResponse.setCreateAt(product.getCreateAt());
        productResponse.setUpdateAt(product.getUpdateAt());
        return productResponse;
    }

}
