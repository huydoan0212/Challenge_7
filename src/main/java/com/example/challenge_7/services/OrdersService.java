package com.example.challenge_7.services;

import com.example.challenge_7.dto.request.OrderRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;

public interface OrdersService {
    OrderResponse createOrder(OrderRequest orderRequest);

    CustomPage<OrderResponse> getOrder(Pageable pageable, String status, String receiverId, String userId);
}
