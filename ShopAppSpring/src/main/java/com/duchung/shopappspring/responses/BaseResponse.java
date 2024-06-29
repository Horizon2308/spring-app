package com.duchung.shopappspring.responses;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    
    protected LocalDateTime createAt;
    protected LocalDateTime updateAt;
}
