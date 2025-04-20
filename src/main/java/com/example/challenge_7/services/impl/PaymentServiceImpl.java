package com.example.challenge_7.services.impl;

import com.example.challenge_7.config.VNPayConfig;
import com.example.challenge_7.dto.response.PaymentResponse;
import com.example.challenge_7.entity.Orders;
import com.example.challenge_7.entity.PaymentStatus;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.repo.OrdersRepository;
import com.example.challenge_7.services.PaymentService;
import com.example.challenge_7.services.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PaymentServiceImpl implements PaymentService {
    OrdersRepository ordersRepository;
    TransactionService transactionService;

    @Override
    public PaymentResponse createOrder(String orderId, long price) throws UnsupportedEncodingException {

        if (!ordersRepository.existsById(orderId)) {
            throw new CustomException(Error.ORDER_NOT_FOUND);
        }

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = price * 100;
        String bankCode = "NCB";

        String vnp_TxnRef = orderId;
        String vnp_IpAddr = "192.168.0.42";

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .URL(paymentUrl)
                .message("Payment successful")
                .status("Success")
                .build();
        return paymentResponse;
    }

    @Override
    public void setStatusOrder(String orderId, String status) {
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new CustomException(Error.ORDER_NOT_FOUND));
        order.setStatus(status);
        ordersRepository.save(order);
    }

    @Override
    public boolean notifyOrder(String vnp_ResponseCode, String vnp_TxnRef, String vnp_TransactionNo, String vnp_TransactionDate, String vnp_Amount) {
        if (vnp_ResponseCode.equals("00")) {
            setStatusOrder(vnp_TxnRef, PaymentStatus.THANH_TOAN_THANH_CONG.getMessage());
            transactionService.createTransaction(vnp_TransactionNo, vnp_TxnRef, vnp_ResponseCode, vnp_TransactionDate, vnp_Amount);
            return true;
        } else {
            transactionService.createTransaction(vnp_TransactionNo, vnp_TxnRef, vnp_ResponseCode, vnp_TransactionDate, vnp_Amount);
            setStatusOrder(vnp_TxnRef, PaymentStatus.THANH_TOAN_THAT_BAI.getMessage());
            return false;
        }
    }

}