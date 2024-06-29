package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExportRawProductDTO {

    private Long id;

    private String name;

    private double price;

    private Long quantity;

    private String note;

    @JsonProperty("store_id")
    private Long storeId;
}
