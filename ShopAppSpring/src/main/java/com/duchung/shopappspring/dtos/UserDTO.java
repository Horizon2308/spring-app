package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "Full name can't be empty")
    @Size(min = 3, max = 30, message = "Full name must be between 3 to 30")
    @JsonProperty("full_name")
    private String fullName;

    @NotEmpty(message = "Phone number can't be empty")
    @JsonProperty("phone_number")
    @Size(min = 1, max = 10, message = "Full name must be between 1 to 10")
    private String phoneNumber;

    @NotEmpty(message = "Address can't be empty")
    private String address;

    @NotEmpty(message = "Password can't be empty")
    private String password;

    @NotEmpty(message = "RePassword can't be empty")
    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("sex")
    private int sex;

    @JsonProperty("cic")
    private String cic;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role_id")
    private Long roleId;
}
