package com.duchung.shopappspring.http_responses;


import java.time.LocalDateTime;

public class SuccessResponse<T> extends BaseResponse<T> {
    public SuccessResponse(T data) {
        super(LocalDateTime.now(), "Success", "", data);
    }

    public SuccessResponse(String message) {
        super(LocalDateTime.now(), "Success", message, null);
    }

    public SuccessResponse(T data, String message) {
        super(LocalDateTime.now(), "Success", message, data);
    }
}
