package com.example.challenge_7.dto.response;

import com.example.challenge_7.entity.OrderDetail;
import com.example.challenge_7.entity.Receiver;
import com.example.challenge_7.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    LocalDateTime orderDate;
    int totalPrice;
    String status;
    UserResponse user;
    List<OrderDetailResponse> orderDetails;
    ReceiverResponse receiver;
}
