package com.duchung.shopappspring.responses;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private String description;
    private String thumbnail;
    private int isActive;
    private Long categoryId;
}
