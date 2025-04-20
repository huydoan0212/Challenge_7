package com.example.challenge_7.services;

import com.example.challenge_7.dto.request.CartCreationRequest;
import com.example.challenge_7.dto.request.CartCreationWithCartItemRequest;
import com.example.challenge_7.dto.response.CartResponse;
import com.example.challenge_7.dto.response.CustomPage;
import org.springframework.data.domain.Pageable;

public interface CartService {

    CartResponse createCart(CartCreationRequest cartCreationRequest);

    CustomPage<CartResponse> getCart(Pageable pageable);

}
