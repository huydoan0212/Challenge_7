package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.response.TransactionResponse;
import com.example.challenge_7.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .responseCode(transaction.getResponseCode())
                .orders(transaction.getOrders())
                .build();
    }
}
