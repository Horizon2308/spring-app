package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.ProductImageDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.models.ProductImage;
import com.duchung.shopappspring.responses.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductImageService {
    ProductImageResponse createProductImage(ProductImageDTO productImageDTO) throws DataNotFoundException;
    ProductImageResponse updateProductImage(Long productImageId,
                                            ProductImageDTO productImageDTO) throws DataNotFoundException;
    void deleteProductImage(Long productImageId);
    ProductImageResponse getProductImage(Long productImageId) throws DataNotFoundException;
    List<ProductImageResponse> getProductImagesByProductId(Long productId) throws DataNotFoundException;
    List<ProductImageResponse> saveAllProductImages(Long productId,
                                                    List<MultipartFile> files) throws DataNotFoundException, InvalidParameterException, IOException;
}
