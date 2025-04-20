package com.example.challenge_7.controller;

import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.PaymentResponse;
import com.example.challenge_7.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Payment")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/createPayment")
    public ApiResponse<?> createPayment(@PathParam("price") long price, @PathParam("orderId") String orderId) throws UnsupportedEncodingException {
        PaymentResponse paymentResponse = paymentService.createOrder(orderId, price);
        return ApiResponse.<PaymentResponse>builder()
                .responseData(paymentResponse)
                .message("Payment created successfully")
                .status("Success")
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/notify")
    public ApiResponse<?> notifyOrder(HttpServletResponse response,
                                      @RequestParam String vnp_Amount,
                                      @RequestParam String vnp_BankCode,
                                      @RequestParam(required = false) String vnp_BankTranNo,
                                      @RequestParam String vnp_CardType,
                                      @RequestParam String vnp_OrderInfo,
                                      @RequestParam String vnp_PayDate,
                                      @RequestParam String vnp_ResponseCode,
                                      @RequestParam String vnp_TmnCode,
                                      @RequestParam String vnp_TransactionNo,
                                      @RequestParam String vnp_TransactionStatus,
                                      @RequestParam String vnp_TxnRef,
                                      @RequestParam String vnp_SecureHash
    ) {

        // Gọi service với các tham số cần thiết
        boolean isSuccess = paymentService.notifyOrder(
                vnp_ResponseCode,
                vnp_TxnRef,
                vnp_TransactionNo,
                vnp_PayDate,
                vnp_Amount
        );

        return ApiResponse.<String>builder()
                .timeStamp(LocalDateTime.now())
                .status(isSuccess ? "Success" : "Fail")
                .message("Payment notify successfully")
                .responseData(isSuccess ? "Thanh toán thành công" : "Thanh toán thất bại")
                .build();
    }


}
