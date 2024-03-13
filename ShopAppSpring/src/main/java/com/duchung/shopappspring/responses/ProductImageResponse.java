package com.duchung.shopappspring.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductImageResponse {
    private Long id;
    private String url;
    private Long productId;
}
