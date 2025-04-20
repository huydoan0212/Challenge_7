package com.example.challenge_7.services.impl;

import com.example.challenge_7.dto.request.OrderRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.OrderResponse;
import com.example.challenge_7.entity.OrderDetail;
import com.example.challenge_7.entity.Orders;
import com.example.challenge_7.entity.PaymentStatus;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.*;
import com.example.challenge_7.repo.OrdersRepository;
import com.example.challenge_7.repo.ReceiverRepository;
import com.example.challenge_7.repo.UserRepository;
import com.example.challenge_7.repo.specification.OrderDetailSpecifications;
import com.example.challenge_7.repo.specification.OrdersSpecifications;
import com.example.challenge_7.services.OrdersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderServiceImpl implements OrdersService {
    OrdersRepository ordersRepository;
    ReceiverRepository receiverRepository;
    UserRepository userRepository;
    OrderDetailMapper orderDetailMapper;
    UserMapper userMapper;
    ReceiverMapper receiverMapper;
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Orders orders = ordersRepository.save(Orders.builder()
                .receiver(receiverRepository.findById(orderRequest.getReceiverId()).orElseThrow(() -> new CustomException(Error.RECEIVER_NOT_FOUND)))
                .orderDate(LocalDateTime.now())
                .totalPrice(orderRequest.getTotalPrice())
                .status(PaymentStatus.CHUA_THANH_TOAN.getMessage())
                .user(userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)))
                .build());
        return OrderResponse.builder()
                .id(orders.getId())
                .orderDate(orders.getOrderDate())
                .totalPrice(orders.getTotalPrice())
                .status(orders.getStatus())
                .user(userMapper.toUserResponse(orders.getUser()))
                .receiver(receiverMapper.toReceiverResponse(orders.getReceiver()))
                .build();
    }

    @Override
    public CustomPage<OrderResponse> getOrder(Pageable pageable, String status, String receiverId, String userId) {
        if (pageable == null || pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable != null ? pageable.getPageNumber() : 0, pageable != null ? pageable.getPageSize() : 10);
        }

        Specification<Orders> spec = Specification.where(null);

        if (StringUtils.hasText(status)) {
            spec = spec.and(OrdersSpecifications.likeStatus(status));
        }

        if (StringUtils.hasText(receiverId)) {
            spec = spec.and(OrdersSpecifications.likeReceiverId(receiverId));
        }
        if (StringUtils.hasText(userId)) {
            spec = spec.and(OrdersSpecifications.likeUserId(userId));
        }

        Page<Orders> orderDetails = ordersRepository.findAll(spec, pageable);
        return PageMapper.toCustomPage(orderDetails.map(orderMapper::toOrderResponse));
    }


}
