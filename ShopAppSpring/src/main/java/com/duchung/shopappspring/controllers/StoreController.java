package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.StoreDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.Store;
import com.duchung.shopappspring.responses.StoreListResponse;
import com.duchung.shopappspring.services.IStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/manager/stores")
public class StoreController {

    private final IStoreService storeService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("")
    public ResponseEntity<?> getAllStores(@RequestParam(defaultValue = "1", value = "sort") int sortOption,
                                          @RequestParam(defaultValue = "", value = "keyword") String keyword,
                                          @RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "12") Integer limit) {
        Page<Store> stores = storeService.getAllStores(keyword, sortOption, page, limit);
        int totalPage = stores.getTotalPages();
        if (totalPage == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Store page is empty!"));
        }
        if (stores.getContent().isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Store page is empty!"));
        }
        return ResponseEntity.ok(new SuccessResponse<>(StoreListResponse.builder()
                .stores(stores)
                .totalPage(totalPage)
                .build()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/without-pages")
    public ResponseEntity<?> getAllStores() {
        return ResponseEntity.ok(new SuccessResponse<>(storeService.getAllStores()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping("")
    public ResponseEntity<?> createStore(@RequestBody StoreDTO storeDTO) {
        storeService.createStore(storeDTO);
        return ResponseEntity.ok(new SuccessResponse<>("Created!"));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStore(@PathVariable("id") Long storeId, @RequestBody StoreDTO storeDTO) {
        try {
            storeService.updateStore(storeId, storeDTO);
            return ResponseEntity.ok(new SuccessResponse<>("Updated!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable("id") Long storeId) {
        try {
            storeService.deleteStore(storeId);
            return ResponseEntity.ok(new SuccessResponse<>("Deleted!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }
}
