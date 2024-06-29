package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.StoreDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStoreService {
    Page<Store> getAllStores(String keyword, int sortOption, Integer page, Integer limit);
    List<Store> getAllStores();
    void createStore(StoreDTO storeDTO);
    void deleteStore(Long storeId) throws DataNotFoundException;
    void updateStore(Long storeId, StoreDTO storeDTO) throws DataNotFoundException;
}
