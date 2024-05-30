package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.CustomerDTO;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.services.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/customers")
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("/search-customers")
    public ResponseEntity<?> searchCustomers(@RequestParam(defaultValue = "", value = "keyword") String keyword) {
        if (keyword.isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Nothing"));
        }
        return ResponseEntity.ok(new SuccessResponse<>(customerService.searchCustomers(keyword)));
    }

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(new SuccessResponse<>(customerService.createCustomer(customerDTO)));
    }
}
