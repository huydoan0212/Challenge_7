package com.example.challenge_7.controller;

import com.example.challenge_7.dto.request.OrderRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.OrderResponse;
import com.example.challenge_7.services.OrdersService;
import com.example.challenge_7.services.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/order")
@Tag(name = "Order")
public class OrderController {
    OrdersService ordersService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = ordersService.createOrder(orderRequest);
        return ApiResponse.<OrderResponse>builder()
                .message("Created Order")
                .timeStamp(LocalDateTime.now())
                .responseData(orderResponse)
                .status(orderResponse != null ? "success" : "fail")
                .build();
    }

    @GetMapping
    public ApiResponse<CustomPage<OrderResponse>> getAllOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @RequestParam(required = false) String status,
                                                               @RequestParam(required = false) String receiverId,
                                                               @RequestParam(required = false) String userId) {
        CustomPage<OrderResponse> customPage = ordersService.getOrder(pageable, status, receiverId, userId);
        return ApiResponse.<CustomPage<OrderResponse>>builder()
                .message("Get order")
                .timeStamp(LocalDateTime.now())
                .responseData(customPage)
                .status(customPage != null ? "success" : "fail")
                .build();
    }
}
