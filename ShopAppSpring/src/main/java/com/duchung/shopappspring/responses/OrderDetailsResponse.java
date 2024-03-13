package com.duchung.shopappspring.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponse extends BaseResponse {

    private Long id;

    private Long orderId;

    private Long productId;

    private double price;

    private int numberOfProducts;

    private double totalMoney;

    private String color;

    private int status;
}
