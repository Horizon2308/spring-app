package com.duchung.shopappspring.responses;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class VNPayResponse <T> extends ResponseEntity<VNPayResponse.Payload<T>> {
    public VNPayResponse(HttpStatusCode code, String message, T data) {
        super(new Payload<>(code.value(), message, data),code);
    }

    @Builder
    public static class Payload<T> {
        public int code;
        public String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public T data;
    }
}