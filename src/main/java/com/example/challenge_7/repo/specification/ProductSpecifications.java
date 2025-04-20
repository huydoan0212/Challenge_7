package com.example.challenge_7.repo.specification;

import com.example.challenge_7.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> likeName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> likeBrand(String brand) {
        if (brand == null || brand.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("brand"), "%" + brand + "%");
    }

    public static Specification<Product> likeCategory(String category) {
        if (category == null || category.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("category"), "%" + category + "%");
    }

    public static Specification<Product> likeType(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("type"), "%" + type + "%");
    }
}
