package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {

    @Min(value = 0, message = "Order id can't be less than 0")
    @JsonProperty("order_id")
    private Long orderId;

    @Min(value = 0, message = "Product id can't be less than 0")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value = 0, message = "Price can't be less than 0")
    private double price;

    @Min(value = 0, message = "Number of product can't be less than 0")
    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @Min(value = 0, message = "Total money can't be less than 0")
    @JsonProperty("total_money")
    private double totalMoney;

    private String color;

}
