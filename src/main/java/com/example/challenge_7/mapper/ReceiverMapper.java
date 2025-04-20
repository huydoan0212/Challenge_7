package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.ReceiverRequest;
import com.example.challenge_7.dto.response.ReceiverResponse;
import com.example.challenge_7.entity.Receiver;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiverMapper {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Receiver toReceiver(ReceiverRequest receiverRequest) {
        return Receiver.builder()
                .receiverPhone(receiverRequest.getReceiverPhone())
                .receiverName(receiverRequest.getReceiverName())
                .user(userRepository.findById(receiverRequest.getUserId()).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)))
                .address(receiverRequest.getAddress())
                .build();
    }

    public ReceiverResponse toReceiverResponse(Receiver receiver) {
        return ReceiverResponse.builder()
                .receiverPhone(receiver.getReceiverPhone())
                .receiverName(receiver.getReceiverName())
                .address(receiver.getAddress())
                .id(receiver.getId())
                .user(userMapper.toUserResponse(receiver.getUser()))
                .build();
    }
}

