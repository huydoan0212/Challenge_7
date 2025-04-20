package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.CartItem;
import com.example.challenge_7.entity.Orders;
import org.springframework.data.jpa.domain.Specification;

public class OrdersSpecifications {
    public static Specification<Orders> likeStatus(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("status"), "%" + status + "%");
    }

    public static Specification<Orders> likeReceiverId(String receiverId) {
        if (receiverId == null || receiverId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("receiver").get("id"), "%" + receiverId + "%");
    }

    public static Specification<Orders> likeUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("user").get("id"), "%" + userId + "%");
    }
}
