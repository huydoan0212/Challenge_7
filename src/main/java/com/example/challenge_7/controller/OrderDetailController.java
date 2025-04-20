package com.example.challenge_7.controller;

import com.example.challenge_7.dto.request.OrderDetailRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CartItemResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.OrderDetailResponse;
import com.example.challenge_7.services.OrderDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/orderDetail")
@Tag(name = "Order detail")
public class OrderDetailController {

    OrderDetailService orderDetailService;

    @PostMapping
    public ApiResponse<OrderDetailResponse> createOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest) {
        OrderDetailResponse orderDetailResponse = orderDetailService.createOrderDetail(orderDetailRequest);
        return ApiResponse.<OrderDetailResponse>builder()
                .message("Created Cart Item")
                .status(orderDetailResponse != null ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(orderDetailResponse)
                .violation(null)
                .build();
    }

    @GetMapping
    ApiResponse<CustomPage<OrderDetailResponse>> getOrderDetail(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                @RequestParam(required = false) String orderId,
                                                                @RequestParam(required = false) String productId) {
        CustomPage<OrderDetailResponse> orderDetailResponse = orderDetailService.getOrderDetail(pageable, orderId, productId);
        return ApiResponse.<CustomPage<OrderDetailResponse>>builder()
                .message("Created Cart Item")
                .status(orderDetailResponse != null ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(orderDetailResponse)
                .violation(null)
                .build();
    }

    @DeleteMapping
    ApiResponse<Void> deleteOrderDetail(@RequestParam String orderId, @RequestParam String productId) {
        return ApiResponse.<Void>builder()
                .message("Delete order detail item")
                .status(orderDetailService.deleteOrderDetail(orderId, productId) ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
