package com.duchung.shopappspring.http_responses;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;
}
