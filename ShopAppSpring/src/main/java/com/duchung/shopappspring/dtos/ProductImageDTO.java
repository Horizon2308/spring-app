package com.duchung.shopappspring.dtos;

import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @Min(value = 1, message = "Product id must be greater than 0")
    private Long productId;

    private String url;
}
