package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.ProviderDTO;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.Provider;
import com.duchung.shopappspring.repositories.ProviderRepository;
import com.duchung.shopappspring.responses.ProviderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/providers")
public class ProviderController {

    private final ProviderRepository providerRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addProvider(@RequestBody ProviderDTO providerDTO) {
        providerRepository.save(Provider.builder()
                        .name(providerDTO.getName())
                        .build());
        return ResponseEntity.ok(new SuccessResponse<>("Add provider successfully!"));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProviders() {
        List<ProviderResponse> listOfProviders = providerRepository.findAll().stream()
                .map(this::convertToProviderResponse)
                .toList();
        return ResponseEntity.ok(new SuccessResponse<>(listOfProviders
                ,"Get all providers successfully!"));
    }

    private ProviderResponse convertToProviderResponse(Provider provider) {
        return ProviderResponse.builder()
                .id(provider.getId())
                .providerName(provider.getName())
                .build();
    }
}
