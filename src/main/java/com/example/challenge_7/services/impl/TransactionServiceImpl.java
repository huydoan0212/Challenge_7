package com.example.challenge_7.services.impl;

import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.TransactionResponse;
import com.example.challenge_7.entity.Transaction;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.mapper.TransactionMapper;
import com.example.challenge_7.repo.OrdersRepository;
import com.example.challenge_7.repo.TransactionRepository;
import com.example.challenge_7.repo.specification.TransactionSpecifications;
import com.example.challenge_7.services.TransactionService;
import jakarta.annotation.security.PermitAll;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    OrdersRepository ordersRepository;
    TransactionMapper transactionMapper;

    @PermitAll
    @Override
    public CustomPage<?> getTransactions(Pageable pageable, String id, String orderId, String responseCode, String transactionDate, String createDate, String amount) {
        // Xác định cách phân trang và sắp xếp
        if (pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }

        // Xác định các tiêu chí tìm kiếm
        Specification<Transaction> spec = Specification.where(null);

        if (StringUtils.hasText(id)) {
            spec = spec.and(TransactionSpecifications.likeId(id));
        }
        if (StringUtils.hasText(orderId)) {
            spec = spec.and(TransactionSpecifications.likeOrderId(orderId));
        }
        if (StringUtils.hasText(responseCode)) {
            spec = spec.and(TransactionSpecifications.likeResponseCode(responseCode));
        }
        if (StringUtils.hasText(transactionDate)) {
            spec = spec.and(TransactionSpecifications.likeTransactionDate(transactionDate));
        }
        if (StringUtils.hasText(createDate)) {
            spec = spec.and(TransactionSpecifications.likeCreateDate(createDate));
        }
        if (StringUtils.hasText(amount)) {
            spec = spec.and(TransactionSpecifications.likeAmount(amount));
        }


        Page<Transaction> transactions = transactionRepository.findAll(spec, pageable);

        CustomPage<TransactionResponse> customPage = PageMapper.toCustomPage(transactions.map(transactionMapper::toTransactionResponse));

        return customPage;
    }

    @PermitAll
    @Override
    public TransactionResponse createTransaction(String id, String orderId, String responseCode, String transactionDate, String amount) {
        Transaction transaction = Transaction.builder()
                .id(id)
                .orders(ordersRepository.findById(orderId).orElseThrow(() -> new CustomException(Error.ORDER_NOT_FOUND)))
                .transactionDate(transactionDate)
                .amount(amount)
                .responseCode(responseCode)
                .build();
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(transaction);
    }
}
