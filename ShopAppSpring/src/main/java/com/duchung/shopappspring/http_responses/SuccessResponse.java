package com.duchung.shopappspring.http_responses;


public class SuccessResponse<T> extends BaseResponse<T> {
    public SuccessResponse(T data) {
        super("Success", "", data);
    }

    public SuccessResponse(String message) {
        super("Success", message, null);
    }

    public SuccessResponse(T data, String message) {
        super("Success", message, data);
    }
}
