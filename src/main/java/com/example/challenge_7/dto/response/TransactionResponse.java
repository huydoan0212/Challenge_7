package com.example.challenge_7.dto.response;

import com.example.challenge_7.entity.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    String id;
    Orders orders;
    String responseCode;
    String transactionDate;
    String createDate;
    String amount;
}
