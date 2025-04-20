package com.example.challenge_7.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiverRequest {
    String address;
    String receiverName;
    String receiverPhone;
    String userId;
}
