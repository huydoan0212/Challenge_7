package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.Receiver;
import com.example.challenge_7.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecifications {
    public static Specification<Transaction> likeId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("id"), "%" + id + "%");
    }

    public static Specification<Transaction> likeResponseCode(String responseCode) {
        if (responseCode == null || responseCode.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("responseCode"), "%" + responseCode + "%");
    }

    public static Specification<Transaction> likeTransactionDate(String transactionDate) {
        if (transactionDate == null || transactionDate.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("transactionDate"), "%" + transactionDate + "%");
    }

    public static Specification<Transaction> likeCreateDate(String createDate) {
        if (createDate == null || createDate.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("createDate"), "%" + createDate + "%");
    }

    public static Specification<Transaction> likeAmount(String amount) {
        if (amount == null || amount.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("amount"), "%" + amount + "%");
    }

    public static Specification<Transaction> likeOrderId(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("orders").get("id"), "%" + orderId + "%");
    }
}
