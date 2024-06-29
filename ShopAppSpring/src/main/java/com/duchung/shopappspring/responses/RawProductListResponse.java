package com.duchung.shopappspring.responses;

import com.duchung.shopappspring.models.RawProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawProductListResponse {
    @JsonProperty("raw_products")
    private Page<RawProduct> rawProducts;

    @JsonProperty("total_page")
    private int totalPage;
}
