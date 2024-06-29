package com.duchung.shopappspring.dtos;

import com.duchung.shopappspring.models.ExportTransactionDocumentDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExportTransactionDocumentDTO {
    private String name;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("raw_products")
    List<ExportRawProductDTO> rawProducts;
}
