package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.RawProductDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Provider;
import com.duchung.shopappspring.models.RawProduct;
import com.duchung.shopappspring.repositories.ProviderRepository;
import com.duchung.shopappspring.repositories.RawProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RawProductService implements IRawProductService {

    private final RawProductRepository rawProductRepository;
    private final ProviderRepository providerRepository;

    @Override
    public Page<RawProduct> getAllRawProducts(Integer sortOption, String keyword, Integer page, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        switch (sortOption) {
            case 1:
                sort = Sort.by(Sort.Direction.DESC, "createAt");
                break;
            case 2:
                sort = Sort.by(Sort.Direction.ASC, "price");
                break;
            case 3:
                sort = Sort.by(Sort.Direction.ASC, "quantity");
                break;
        }
        return rawProductRepository.searchRawProducts(keyword, PageRequest.of(page, limit, sort));
    }

    @Transactional
    @Override
    public void createRawProduct(RawProductDTO rawProductDTO) throws DataNotFoundException {
        Provider provider = providerRepository.findById(rawProductDTO.getProviderId())
                .orElseThrow(() -> new DataNotFoundException("Provider not found!"));
        rawProductRepository.save(convertToRawProduct(rawProductDTO, provider));
    }

    @Transactional
    @Override
    public void deleteRawProduct(Long rawProductId) throws DataNotFoundException {
        RawProduct deletedRawProduct = rawProductRepository.findById(rawProductId)
                .orElseThrow(() -> new DataNotFoundException("Raw product not found!"));
        rawProductRepository.delete(deletedRawProduct);
    }

    @Transactional
    @Override
    public void updateRawProduct(Long rawProductId, RawProductDTO rawProductDTO) {

    }

    @Override
    public List<RawProduct> searchRawProducts(String keyword) {
        return rawProductRepository.searchRawProducts(keyword);
    }

    public RawProduct convertToRawProduct(RawProductDTO rawProductDTO, Provider provider) {
          return RawProduct.builder()
                  .name(rawProductDTO.getName())
                  .note(rawProductDTO.getNote())
                  .price(rawProductDTO.getPrice())
                  .provider(provider)
                  .quantity(rawProductDTO.getQuantity())
                  .build();
    }
}
