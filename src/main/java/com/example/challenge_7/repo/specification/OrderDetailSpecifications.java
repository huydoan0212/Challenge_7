package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.OrderDetail;
import org.springframework.data.jpa.domain.Specification;

public class OrderDetailSpecifications {

    public static Specification<OrderDetail> likeOrderId(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("orders").get("id"), "%" + orderId + "%");
    }

    public static Specification<OrderDetail> likeProductId(String productId) {
        if (productId == null || productId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("product").get("id"), "%" + productId + "%");
    }
}
