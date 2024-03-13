package com.duchung.shopappspring.responses;

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
public class ProductListResponse {

    @JsonProperty("products")
    private Page<ProductResponse> productResponses;

    @JsonProperty("total_page")
    private int totalPage;
}
