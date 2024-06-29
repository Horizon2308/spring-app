package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.StoreDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Store;
import com.duchung.shopappspring.repositories.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;

    @Override
    public Page<Store> getAllStores(String keyword, int sortOption, Integer page, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        switch (sortOption) {
            case 1:
                sort = Sort.by(Sort.Direction.DESC, "createAt");
                break;
            case 2:
                sort = Sort.by(Sort.Direction.ASC, "name");
                break;
        }
        return storeRepository.searchStores(keyword, PageRequest.of(page, limit, sort));
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Transactional
    @Override
    public void createStore(StoreDTO storeDTO) {
        storeRepository.save(convertToStore(storeDTO));
    }

    @Transactional
    @Override
    public void deleteStore(Long storeId) throws DataNotFoundException {
        Store deletedStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new DataNotFoundException("Store not found !"));
        storeRepository.delete(deletedStore);
    }

    @Transactional
    @Override
    public void updateStore(Long storeId, StoreDTO storeDTO) throws DataNotFoundException {
        Store updatedStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new DataNotFoundException("Store not found!"));
        updatedStore.setAddress(storeDTO.getAddress());
        updatedStore.setName(storeDTO.getName());
        updatedStore.setPhoneNumber(storeDTO.getPhoneNumber());
        storeRepository.save(updatedStore);
    }

    private Store convertToStore(StoreDTO storeDTO) {
        return Store.builder()
                .name(storeDTO.getName())
                .address(storeDTO.getAddress())
                .phoneNumber(storeDTO.getPhoneNumber())
                .build();
    }
}
