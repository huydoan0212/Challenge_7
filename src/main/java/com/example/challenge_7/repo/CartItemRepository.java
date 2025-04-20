package com.example.challenge_7.repo;

import com.example.challenge_7.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String>, JpaSpecificationExecutor<CartItem> {
}
