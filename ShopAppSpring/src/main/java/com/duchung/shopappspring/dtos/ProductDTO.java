package com.duchung.shopappspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @NotEmpty(message = "Name field can't be empty")
    @Size(min = 3, max = 200, message = "Name field must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price field must be greater than 0 or equals to 0")
    @Max(value = 10000000, message = "Price field must be less than 10.000.000 or equals to 10.000.000")
    private double price;

    private String thumbnail;

    private Long quantity;

    private String description;

    private List<MultipartFile> files;

    @JsonProperty("category_id")
    private Long categoryId;
}
