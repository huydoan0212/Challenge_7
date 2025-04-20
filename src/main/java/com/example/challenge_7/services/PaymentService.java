package com.example.challenge_7.services;

import com.example.challenge_7.dto.response.PaymentResponse;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    PaymentResponse createOrder(String orderId, long price) throws UnsupportedEncodingException;

    void setStatusOrder(String orderId, String status) throws UnsupportedEncodingException;

    boolean notifyOrder(String vnp_ResponseCode, String vnp_TxnRef, String vnp_TransactionNo, String vnp_TransactionDate, String vnp_Amount);

}
