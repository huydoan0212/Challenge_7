package com.example.challenge_7.services;

import com.example.challenge_7.dto.request.OrderDetailRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.OrderDetailResponse;
import org.springframework.data.domain.Pageable;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest);

    CustomPage<OrderDetailResponse> getOrderDetail(Pageable pageable, String orderId, String productId);

    boolean deleteOrderDetail(String orderId, String productId);
}
