package com.example.challenge_7.dto.response;

import com.example.challenge_7.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    String id;
    UserResponse userResponse;
    List<CartItemResponse> cartItems;
}
