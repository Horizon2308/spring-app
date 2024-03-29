package com.duchung.shopappspring.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Phone number can't be empty!")
    @Size(min = 9, max = 10, message = "Phone number must be between 9 to 10")
    private String phoneNumber;

    @NotEmpty(message = "Password can't be empty!")
    private String password;
}
