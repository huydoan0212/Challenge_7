package com.example.challenge_7.services;

import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.TransactionResponse;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    CustomPage<?> getTransactions(Pageable pageable, String id, String orderId, String responseCode, String transactionDate, String createDate, String amount);

    TransactionResponse createTransaction(String id, String orderId, String responseCode, String transactionDate, String amount);

}
