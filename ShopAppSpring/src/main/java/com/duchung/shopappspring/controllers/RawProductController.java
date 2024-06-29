package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.RawProduct;
import com.duchung.shopappspring.responses.RawProductListResponse;
import com.duchung.shopappspring.services.IRawProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/manager/raw-products")
public class RawProductController {

    private final IRawProductService rawProductService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("")
    public ResponseEntity<?> getAllRawProducts(@RequestParam(defaultValue = "1", value = "sort") int sortOption,
                                               @RequestParam(defaultValue = "", value = "keyword") String keyword,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "12") Integer limit) {
        Page<RawProduct> rawProducts = rawProductService
                .getAllRawProducts(sortOption, keyword, page, limit);
        int totalPage = rawProducts.getTotalPages();
        if (totalPage == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Raw products page is empty!"));
        }
        if (rawProducts.getContent().isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Raw products page is empty!"));
        }
        return ResponseEntity.ok(new SuccessResponse<>(RawProductListResponse.builder()
                .rawProducts(rawProducts)
                .totalPage(totalPage)
                .build()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/search")
    public ResponseEntity<?> searchRawProducts(@RequestParam(defaultValue = "", value = "keyword") String keyword) {
        if (keyword.isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Nothing!"));
        }
        return ResponseEntity.ok(new SuccessResponse<>(rawProductService.searchRawProducts(keyword)));
    }
}
