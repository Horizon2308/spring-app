package com.duchung.shopappspring.responses;

import com.duchung.shopappspring.models.Store;
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
public class StoreListResponse {

    @JsonProperty("stores")
    private Page<Store> stores;

    @JsonProperty("total_page")
    private int totalPage;
}
