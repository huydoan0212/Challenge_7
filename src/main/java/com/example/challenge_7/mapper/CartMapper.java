package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.CartCreationWithCartItemRequest;
import com.example.challenge_7.dto.response.CartResponse;
import com.example.challenge_7.entity.Cart;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CartMapper {
    UserRepository userRepository;
    CartItemMapper cartItemMapper;
    UserMapper userMapper;

    public Cart toCart(CartCreationWithCartItemRequest request) {
        return Cart.builder()
                .user(userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)))
                .build();
    }

    public CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userResponse(userMapper.toUserResponse(cart.getUser()))
                .cartItems(cart.getCartItems().stream().map(cartItemMapper::toCartItemResponse).toList())
                .build();
    }
}
