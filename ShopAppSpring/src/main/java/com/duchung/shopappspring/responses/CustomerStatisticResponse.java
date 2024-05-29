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
public class CustomerStatisticResponse {

    private Long id;

    @JsonProperty("user_name")
    private String name;

    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
