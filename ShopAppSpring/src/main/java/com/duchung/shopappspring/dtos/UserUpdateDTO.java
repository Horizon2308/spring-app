package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {

    private Long id;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private String avatar;

    @JsonProperty("role_id")
    private Long roleId;
}
