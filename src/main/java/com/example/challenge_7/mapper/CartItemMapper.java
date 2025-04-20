package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.CartItemRequest;
import com.example.challenge_7.dto.response.CartItemResponse;
import com.example.challenge_7.entity.Cart;
import com.example.challenge_7.entity.CartItem;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.repo.CartRepository;
import com.example.challenge_7.repo.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class CartItemMapper {

    CartRepository cartRepository;
    ProductRepository productRepository;

    public CartItem toCartItem(CartItemRequest request, String cartId) {
        return CartItem.builder()
                .cart(cartRepository.findById(cartId).orElseThrow(() -> new CustomException(Error.CART_NOT_FOUND)))
                .product(productRepository.findById(request.getProductId()).orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND)))
                .quantity(request.getQuantity())
                .build();
    }

    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .cart(cartItem.getCart())
                .id(cartItem.getId())
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
