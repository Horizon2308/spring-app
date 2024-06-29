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
public class TransactionDocumentResponse {

    private Long id;

    private String name;

    private String username;

    @JsonProperty("total_products")
    private int totalProducts;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private String type;
}
