package com.duchung.shopappspring.http_responses;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse<T> extends BaseResponse<T> {
    public ErrorResponse(T data, String message) {
        super(LocalDateTime.now(), "Error", message, data);
    }

    public ErrorResponse(String message) {
        super(LocalDateTime.now(), "Error", message, null);
    }

    public ErrorResponse(List<String> messages) {
        super(LocalDateTime.now(), "Error", messages != null ? messages.get(0) : "Message is empty", null);
    }
}
