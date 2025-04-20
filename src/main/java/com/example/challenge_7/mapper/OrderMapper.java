package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.OrderRequest;
import com.example.challenge_7.dto.response.OrderResponse;
import com.example.challenge_7.entity.Orders;
import com.example.challenge_7.entity.PaymentStatus;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.repo.ReceiverRepository;
import com.example.challenge_7.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderMapper {
    UserRepository userRepository;
    ReceiverRepository receiverRepository;
    OrderDetailMapper orderDetailMapper;
    ReceiverMapper receiverMapper;
    UserMapper userMapper;

    public Orders toOrders(OrderRequest orderRequest) {
        return Orders.builder()
                .user(userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)))
                .orderDate(LocalDateTime.now())
                .totalPrice(orderRequest.getTotalPrice())
                .receiver(receiverRepository.findById(orderRequest.getReceiverId()).orElseThrow(() -> new CustomException(Error.RECEIVER_NOT_FOUND)))
                .status(PaymentStatus.CHUA_THANH_TOAN.getMessage())
                .build();
    }

    public OrderResponse toOrderResponse(Orders orders) {
        return OrderResponse.builder()
                .id(orders.getId())
                .orderDetails(orders.getOrderDetails().stream().map(orderDetailMapper::toOrderDetailResponse).toList())
                .status(orders.getStatus())
                .orderDate(orders.getOrderDate())
                .totalPrice(orders.getTotalPrice())
                .receiver(receiverMapper.toReceiverResponse(orders.getReceiver()))
                .user(userMapper.toUserResponse(orders.getUser()))
                .build();
    }

}
