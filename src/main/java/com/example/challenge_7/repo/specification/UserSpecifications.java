package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> likeFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get("firstName"), "%" + firstName + "%");
        };
    }

    public static Specification<User> likeLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get("lastName"), "%" + lastName + "%");
        };
    }

    public static Specification<User> likeUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.equal(root.get("username"), username);
        };
    }
}
