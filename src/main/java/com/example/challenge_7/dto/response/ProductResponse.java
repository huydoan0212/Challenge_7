package com.example.challenge_7.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String code;
    String name;
    String category;
    String brand;
    String type;
    String description;
    Integer price;
}
