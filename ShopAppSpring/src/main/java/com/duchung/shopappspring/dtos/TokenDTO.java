package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    @NotEmpty(message = "Token can't be blank")
    private String token;

    @NotEmpty(message = "Token type can't be blank")
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expiration_date")
    private LocalDateTime expirationDate;

    @JsonProperty("user_id")
    @Min(value = 0, message = "User id must be greater than 0")
    private int userId;
}
