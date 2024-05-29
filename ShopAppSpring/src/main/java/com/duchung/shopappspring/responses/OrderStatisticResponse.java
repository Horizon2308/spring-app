package com.duchung.shopappspring.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatisticResponse {

    private Long id;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("total_money")
    private Double totalMoney;

    private String status;
}
