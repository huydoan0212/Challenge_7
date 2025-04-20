package com.example.challenge_7.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter

public enum Error {
    UNCATEGORIZED_EXCEPTION("Uncategorized exception", "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("Invalid message key", "Invalid message key", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND("Product not found", "Product not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated", "Your token cannot be authenticated ", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission", "You do not have permission", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND("User not found", "User not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("Role not found", "Role not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED("User not existed", "User not existed", HttpStatus.NOT_FOUND),
    CAN_NOT_CHANGE_JAVA_TO_JSON("Can not change Java to JSON", "Can not change Java to JSON", HttpStatus.BAD_REQUEST),
    USER_EXISTED("User existed", "User existed", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED("Role existed", "Role existed", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND("Cart not found", "Cart not found", HttpStatus.NOT_FOUND),
    SORT_CONDITION_NOT_FOUND("Sort condition not found", "Sort condition not found", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_FOUND("Cart item not found", "Cart item not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("Order not found", "Order not found", HttpStatus.NOT_FOUND),
    RECEIVER_NOT_FOUND("Receiver not found", "Receiver not found", HttpStatus.NOT_FOUND);
    private final String message;
    private final String violation;
    private final HttpStatusCode statusCode;

    Error(String message, String violation, HttpStatusCode statusCode) {
        this.message = message;
        this.violation = violation;
        this.statusCode = statusCode;
    }
}
