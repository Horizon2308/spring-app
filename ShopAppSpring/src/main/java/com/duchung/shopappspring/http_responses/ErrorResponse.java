package com.duchung.shopappspring.http_responses;

import java.util.List;

public class ErrorResponse<T> extends BaseResponse<T> {
    public ErrorResponse(T data, String message) {
        super("Error", message, data);
    }

    public ErrorResponse(String message) {
        super("Error", message, null);
    }

    public ErrorResponse(List<String> messages) {
        super("Error", messages != null ? messages.get(0) : "Message is empty", null);
    }
}
