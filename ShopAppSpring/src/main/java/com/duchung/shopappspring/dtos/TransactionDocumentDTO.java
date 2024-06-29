package com.duchung.shopappspring.dtos;


import com.duchung.shopappspring.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDocumentDTO {
    private String name;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("raw_products")
    List<RawProductDTO> rawProducts;
}
