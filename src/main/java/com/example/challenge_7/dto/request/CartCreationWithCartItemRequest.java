package com.example.challenge_7.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartCreationWithCartItemRequest {
    String userId;
    List<CartItemRequest> cartItemRequests;
}
