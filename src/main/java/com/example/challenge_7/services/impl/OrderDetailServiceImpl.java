package com.example.challenge_7.services.impl;

import com.example.challenge_7.dto.request.OrderDetailRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.OrderDetailResponse;
import com.example.challenge_7.entity.CartItem;
import com.example.challenge_7.entity.OrderDetail;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.OrderDetailMapper;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.repo.OrderDetailRepository;
import com.example.challenge_7.repo.OrdersRepository;
import com.example.challenge_7.repo.ProductRepository;
import com.example.challenge_7.repo.specification.CartItemSpecifications;
import com.example.challenge_7.repo.specification.OrderDetailSpecifications;
import com.example.challenge_7.services.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    ProductRepository productRepository;
    OrdersRepository ordersRepository;

    @Lazy
    OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest) {
        OrderDetail orderDetail = orderDetailRepository.save(
                OrderDetail.builder()
                        .product(productRepository.findById(orderDetailRequest.getProductId()).orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND)))
                        .quantity(orderDetailRequest.getQuantity())
                        .orders(ordersRepository.findById(orderDetailRequest.getOrderId()).orElseThrow(() -> new CustomException(Error.ORDER_NOT_FOUND)))
                        .build()
        );
        return orderDetailMapper.toOrderDetailResponse(orderDetail);
    }

    @Override
    public CustomPage<OrderDetailResponse> getOrderDetail(Pageable pageable, String orderId, String productId) {
        if (pageable == null || pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable != null ? pageable.getPageNumber() : 0, pageable != null ? pageable.getPageSize() : 10);
        }

        Specification<OrderDetail> spec = Specification.where(null);

        if (StringUtils.hasText(orderId)) {
            spec = spec.and(OrderDetailSpecifications.likeOrderId(orderId));
        }

        if (StringUtils.hasText(productId)) {
            spec = spec.and(OrderDetailSpecifications.likeProductId(productId));
        }

        Page<OrderDetail> orderDetails = orderDetailRepository.findAll(spec, pageable);
        return PageMapper.toCustomPage(orderDetails.map(orderDetailMapper::toOrderDetailResponse));
    }

    @Override
    public boolean deleteOrderDetail(String orderId, String productId) {
        Specification<OrderDetail> spec = Specification.where(null);

        if (StringUtils.hasText(orderId)) {
            spec = spec.and(OrderDetailSpecifications.likeOrderId(orderId));
        }
        if (StringUtils.hasText(productId)) {
            spec = spec.and(OrderDetailSpecifications.likeProductId(productId));
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findAll(spec);
        if (!orderDetails.isEmpty()) {
            orderDetailRepository.deleteAll(orderDetails);
            return true;
        }
        return false;
    }
}

