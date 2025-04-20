package com.example.challenge_7.controller;

import com.example.challenge_7.dto.request.CartCreationRequest;
import com.example.challenge_7.dto.request.CartCreationWithCartItemRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CartResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/cart")
@Tag(name = "Cart")
public class CartController {
    CartService cartService;

    @GetMapping
    public ApiResponse<CustomPage<CartResponse>> getAllCarts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        CustomPage<CartResponse> page = cartService.getCart(pageable);
        return ApiResponse.<CustomPage<CartResponse>>builder()
                .message("Get all cart")
                .status(page != null ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(page)
                .violation(null)
                .build();
    }


    @PostMapping
    ApiResponse<CartResponse> createCart(@RequestBody CartCreationRequest cartCreationRequest) {
        CartResponse cartResponse = cartService.createCart(cartCreationRequest);
        return ApiResponse.<CartResponse>builder()
                .message("Create cart")
                .status(cartResponse != null ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(cartResponse)
                .violation(null)
                .build();
    }
}
