package com.example.challenge_7.dto.response;

import com.example.challenge_7.entity.Orders;
import com.example.challenge_7.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiverResponse {
    String id;
    String address;
    String receiverName;
    String receiverPhone;
    UserResponse user;
}
