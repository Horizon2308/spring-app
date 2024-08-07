package com.duchung.shopappspring.responses;

import com.duchung.shopappspring.models.Comment;
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
public class CommentListResponse {

    private Page<Comment> comments;

    @JsonProperty("total_pages")
    private int totalPages;
}
