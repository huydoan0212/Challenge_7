package com.example.challenge_7.services;

import com.example.challenge_7.dto.request.CartItemRequest;
import com.example.challenge_7.dto.response.CartItemResponse;
import com.example.challenge_7.dto.response.CustomPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartItemService {
    CartItemResponse createCartItem(CartItemRequest cartItemRequest);

    CustomPage<CartItemResponse> getCartItems(Pageable pageable, String cartId, String productId);

    boolean deleteCartItem(String cartId, String productId);
}
