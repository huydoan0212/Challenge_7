package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.OrderDetailRequest;
import com.example.challenge_7.dto.response.OrderDetailResponse;
import com.example.challenge_7.entity.OrderDetail;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.repo.OrdersRepository;
import com.example.challenge_7.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderDetailMapper {
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrders().getId())
                .quantity(orderDetail.getQuantity())
                .product(productMapper.toProductResponse(orderDetail.getProduct()))
                .build();
    }

    // Nếu cần thiết, sử dụng @Lazy để phá vỡ vòng lặp
    public OrderDetail toOrderDetail(@Lazy OrderDetailRequest orderDetailRequest) {
        return OrderDetail.builder()
                .orders(ordersRepository.findById(orderDetailRequest.getOrderId()).orElseThrow(() -> new CustomException(Error.ORDER_NOT_FOUND)))
                .quantity(orderDetailRequest.getQuantity())
                .product(productRepository.findById(orderDetailRequest.getProductId()).orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND)))
                .build();
    }
}

