package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.RawProductDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.RawProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRawProductService {
    Page<RawProduct> getAllRawProducts(Integer sortOption, String keyword, Integer page, Integer limit);
    void createRawProduct(RawProductDTO rawProductDTO) throws DataNotFoundException;
    void deleteRawProduct(Long rawProductId) throws DataNotFoundException;
    void updateRawProduct(Long rawProductId, RawProductDTO rawProductDTO);
    List<RawProduct> searchRawProducts(String keyword);
}
