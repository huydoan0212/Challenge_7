package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.Receiver;
import org.springframework.data.jpa.domain.Specification;

public class ReceiverSpecifications {
    public static Specification<Receiver> likeAddress(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("address"), "%" + address + "%");
    }

    public static Specification<Receiver> likeReceiverName(String receiverName) {
        if (receiverName == null || receiverName.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("receiverName"), "%" + receiverName + "%");
    }

    public static Specification<Receiver> likeReceiverPhone(String receiverPhone) {
        if (receiverPhone == null || receiverPhone.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("receiverPhone"), "%" + receiverPhone + "%");
    }

    public static Specification<Receiver> likeUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("user").get("id"), "%" + userId + "%");
    }

    public static Specification<Receiver> likeId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("id"), "%" + id + "%");
    }
}
