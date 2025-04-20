package com.example.challenge_7.services;


import com.example.challenge_7.dto.request.ReceiverRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.ReceiverResponse;
import org.springframework.data.domain.Pageable;

public interface ReceiverService {
    ReceiverResponse createReceiver(ReceiverRequest receiverRequest);

    CustomPage<ReceiverResponse> getReceiver(Pageable pageable, String id, String address, String receiverName, String receiverPhone, String userId);

    boolean deleteReceiver(String id, String address, String receiverName, String receiverPhone, String userId);
}
