package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.CartItem;
import com.example.challenge_7.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class CartItemSpecifications {
    public static Specification<CartItem> likeCartId(String cartId) {
        if (cartId == null || cartId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("cart").get("id"), "%" + cartId + "%");
    }

    public static Specification<CartItem> likeProductId(String productId) {
        if (productId == null || productId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("product").get("id"), "%" + productId + "%");
    }
}
