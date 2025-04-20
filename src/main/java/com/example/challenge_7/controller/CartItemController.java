package com.example.challenge_7.controller;

import com.example.challenge_7.dto.request.CartItemRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CartItemResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.entity.CartItem;
import com.example.challenge_7.services.CartItemService;
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
@RequestMapping("/cartItem")
@Tag(name = "Cart Item")
public class CartItemController {
    CartItemService cartItemService;

    @PostMapping
    public ApiResponse<CartItemResponse> createCartItem(@RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse cartItemResponse = cartItemService.createCartItem(cartItemRequest);
        return ApiResponse.<CartItemResponse>builder()
                .message("Created Cart Item")
                .status(cartItemResponse != null ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(cartItemResponse)
                .violation(null)
                .build();
    }

    @GetMapping
    ApiResponse<CustomPage<CartItemResponse>> getCartItem(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) String cartId,
                                                          @RequestParam(required = false) String productId) {
        CustomPage<CartItemResponse> cartItemResponse = cartItemService.getCartItems(pageable, cartId, productId);
        return ApiResponse.<CustomPage<CartItemResponse>>builder()
                .message("Get cart item")
                .status(cartItemResponse != null ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(cartItemResponse)
                .violation(null)
                .build();
    }

    @DeleteMapping
    ApiResponse<Void> deleteCartItem(@RequestParam(required = false) String cartId, @RequestParam(required = false) String productId) {
        return ApiResponse.<Void>builder()
                .message("Delete cart item")
                .status(cartItemService.deleteCartItem(cartId, productId) ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
