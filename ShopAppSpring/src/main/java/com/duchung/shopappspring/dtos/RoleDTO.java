package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @JsonProperty("role_name")
    private String name;
}
