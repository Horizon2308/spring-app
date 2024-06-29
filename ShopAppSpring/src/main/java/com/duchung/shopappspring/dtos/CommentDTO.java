package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    @JsonProperty("user_id")
    private Long userId;

    private String content;

    private Long likes;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("product_id")
    private Long productId;
}
