package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "Phone number can't be empty")
    @JsonProperty("phone_number")
    @Size(min = 1, max = 10, message = "Phone number must has size between 1 to 10")
    private String phoneNumber;

    @NotEmpty(message = "Password can't be empty")
    private String password;
}
