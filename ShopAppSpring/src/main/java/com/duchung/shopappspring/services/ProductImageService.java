package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.ProductDTO;
import com.duchung.shopappspring.dtos.ProductImageDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.models.ProductImage;
import com.duchung.shopappspring.repositories.ProductImageRepository;
import com.duchung.shopappspring.repositories.ProductRepository;
import com.duchung.shopappspring.responses.ProductImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductImageResponse createProductImage(ProductImageDTO productImageDTO) throws DataNotFoundException {
        Product product = productRepository.findById(productImageDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        ProductImage productImage = productImageRepository.save(convertToProductImage(productImageDTO, product));
        return convertToProductImageResponse(productImage);
    }

    @Override
    public ProductImageResponse updateProductImage(Long productImageId,
                                                   ProductImageDTO productImageDTO) throws DataNotFoundException  {
        ProductImage updatedProduct = productImageRepository.findById(productImageId)
                .orElseThrow(() -> new DataNotFoundException("Product image not found!"));
        updatedProduct.setUrl(productImageDTO.getUrl());
        return convertToProductImageResponse(productImageRepository.save(updatedProduct));
    }

    @Override
    public void deleteProductImage(Long productImageId) {
        productImageRepository.deleteById(productImageId);
    }

    @Override
    public ProductImageResponse getProductImage(Long productImageId) throws DataNotFoundException {
        ProductImage productImage = productImageRepository.findById(productImageId)
                .orElseThrow(() -> new DataNotFoundException("Product image not found!"));
        return convertToProductImageResponse(productImage);
    }

    @Override
    public List<ProductImageResponse> getProductImagesByProductId(Long productId) throws DataNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        return productImageRepository.findAllByProduct(product)
                .stream()
                .map(this::convertToProductImageResponse)
                .toList();
    }

    @Override
    public List<ProductImageResponse> saveAllProductImages(Long productId,
                                                           List<MultipartFile> files)
            throws DataNotFoundException, IOException, InvalidParameterException {
        List<ProductImageResponse> productImageResponses = new ArrayList<>();
        for (var file : files) {
           String fileName = storeFile(handleMultipartFile(file));
           productImageResponses.add(createProductImage(ProductImageDTO.builder()
                   .productId(productId)
                   .url(fileName)
                   .build()));
        }
        return productImageResponses;
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // get the file name
        // append a random unique string to identify each images
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        // get the file path
        Path uploadDir = Paths.get("uploads");
        // if file path doesn't exist, create a new file path with path name is uploads
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // get the full file path to save the file
        Path destination = Paths.get(uploadDir.toString(), fileName);
        // then save the file
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private MultipartFile handleMultipartFile(MultipartFile file) throws InvalidParameterException {
        if (file == null || file.getSize() == 0) {
            throw new InvalidParameterException(file.getOriginalFilename() + " is invalid image");
        }
        if (file.getSize() > 10 + 1024) {
            throw new InvalidParameterException(file.getOriginalFilename() + " is too large! Maximum size is 10MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidParameterException(file.getOriginalFilename() + " is invalid image");
        }
        return file;
    }

    private ProductImage convertToProductImage(ProductImageDTO productImageDTO, Product product) {
        return ProductImage.builder()
                .url(productImageDTO.getUrl())
                .product(product)
                .build();
    }

    private ProductImageResponse convertToProductImageResponse(ProductImage productImage) {
        return ProductImageResponse.builder()
                .url(productImage.getUrl())
                .productId(productImage.getProduct().getId())
                .id(productImage.getId())
                .build();
    }
}
