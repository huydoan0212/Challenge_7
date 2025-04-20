package com.example.challenge_7.services.impl;

import com.example.challenge_7.dto.request.ReceiverRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.ReceiverResponse;
import com.example.challenge_7.entity.Receiver;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.mapper.ReceiverMapper;
import com.example.challenge_7.repo.ReceiverRepository;
import com.example.challenge_7.repo.UserRepository;
import com.example.challenge_7.repo.specification.ReceiverSpecifications;
import com.example.challenge_7.services.ReceiverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ReceiverServiceImpl implements ReceiverService {
    ReceiverRepository receiverRepository;
    UserRepository userRepository;
    ReceiverMapper receiverMapper;

    @Override
    public ReceiverResponse createReceiver(ReceiverRequest receiverRequest) {
        Receiver receiver = receiverRepository.save(
                Receiver.builder()
                        .address(receiverRequest.getAddress())
                        .user(userRepository.findById(receiverRequest.getUserId()).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)))
                        .receiverName(receiverRequest.getReceiverName())
                        .receiverPhone(receiverRequest.getReceiverPhone())
                        .build()
        );

        return receiverMapper.toReceiverResponse(receiver);
    }

    @Override
    public CustomPage<ReceiverResponse> getReceiver(Pageable pageable, String id, String address, String receiverName, String receiverPhone, String userId) {
        if (pageable == null || pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable != null ? pageable.getPageNumber() : 0, pageable != null ? pageable.getPageSize() : 10);
        }

        Specification<Receiver> spec = Specification.where(null);

        if (StringUtils.hasText(id)) {
            spec = spec.and(ReceiverSpecifications.likeId(id));
        }
        if (StringUtils.hasText(address)) {
            spec = spec.and(ReceiverSpecifications.likeAddress(address));
        }
        if (StringUtils.hasText(receiverName)) {
            spec = spec.and(ReceiverSpecifications.likeReceiverName(receiverName));
        }
        if (StringUtils.hasText(receiverPhone)) {
            spec = spec.and(ReceiverSpecifications.likeReceiverPhone(receiverPhone));
        }
        if (StringUtils.hasText(userId)) {
            spec = spec.and(ReceiverSpecifications.likeUserId(userId));
        }
        Page<Receiver> receivers = receiverRepository.findAll(spec, pageable);
        return PageMapper.toCustomPage(receivers.map(receiverMapper::toReceiverResponse));
    }

    @Override
    public boolean deleteReceiver(String id, String address, String receiverName, String receiverPhone, String userId) {
        Specification<Receiver> spec = Specification.where(null);

        if (StringUtils.hasText(id)) {
            spec = spec.and(ReceiverSpecifications.likeId(id));
        }
        if (StringUtils.hasText(address)) {
            spec = spec.and(ReceiverSpecifications.likeAddress(address));
        }
        if (StringUtils.hasText(receiverName)) {
            spec = spec.and(ReceiverSpecifications.likeReceiverName(receiverName));
        }
        if (StringUtils.hasText(receiverPhone)) {
            spec = spec.and(ReceiverSpecifications.likeReceiverPhone(receiverPhone));
        }
        if (StringUtils.hasText(userId)) {
            spec = spec.and(ReceiverSpecifications.likeUserId(userId));
        }
        List<Receiver> receivers = receiverRepository.findAll(spec);
        if (!receivers.isEmpty()) {
            receiverRepository.deleteAll(receivers);
            return true;
        }
        return false;
    }
}
