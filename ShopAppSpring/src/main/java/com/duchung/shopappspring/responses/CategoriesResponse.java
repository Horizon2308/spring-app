package com.duchung.shopappspring.responses;

import com.duchung.shopappspring.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriesResponse {

    private Page<Category> categories;

    @JsonProperty("total_page")
    private int totalPage;
}
