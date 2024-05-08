package com.duchung.shopappspring.responses;

import com.duchung.shopappspring.models.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWithoutCategoryResponse extends BaseResponse {

    private Long id;

    private String name;

    private String description;

    private String thumbnail;

    private Double price;

    private Long quantity;

    @JsonProperty("product_status")
    private int status;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("product_images")
    private List<ProductImage> productImages;

    @JsonProperty("category_id")
    private Long categoryId;
}