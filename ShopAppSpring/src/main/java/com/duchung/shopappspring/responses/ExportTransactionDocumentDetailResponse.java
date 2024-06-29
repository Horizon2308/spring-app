package com.duchung.shopappspring.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportTransactionDocumentDetailResponse {
    private Long id;

    @JsonProperty("raw_product_name")
    private String rawProductName;

    private double price;

    private Long quantity;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
