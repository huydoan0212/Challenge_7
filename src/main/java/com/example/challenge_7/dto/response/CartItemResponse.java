package com.example.challenge_7.dto.response;

import com.example.challenge_7.entity.Cart;
import com.example.challenge_7.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    String id;
    int quantity;
    Cart cart;
    Product product;
}
