package com.example.challenge_7.controller;

import com.example.challenge_7.dto.request.ReceiverRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.ReceiverResponse;
import com.example.challenge_7.services.ReceiverService;
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
@RequestMapping("/api/receiver")
@Tag(name = "Receiver")
public class ReceiverController {
    ReceiverService receiverService;

    @PostMapping
    public ApiResponse<ReceiverResponse> createReceiver(@RequestBody ReceiverRequest receiverRequest) {
        ReceiverResponse receiverResponse = receiverService.createReceiver(receiverRequest);
        return ApiResponse.<ReceiverResponse>builder()
                .message("Create receiver")
                .status("success")
                .timeStamp(LocalDateTime.now())
                .responseData(receiverResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<CustomPage<ReceiverResponse>> getReceiver(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                 @RequestParam(required = false) String id,
                                                                 @RequestParam(required = false) String address,
                                                                 @RequestParam(required = false) String receiverName,
                                                                 @RequestParam(required = false) String receiverPhone,
                                                                 @RequestParam(required = false) String userId) {
        CustomPage<ReceiverResponse> customPage = receiverService.getReceiver(pageable, id, address, receiverName, receiverPhone, userId);
        return ApiResponse.<CustomPage<ReceiverResponse>>builder()
                .message("Get receiver")
                .status(!customPage.isEmpty() ? "success" : "fail")
                .timeStamp(LocalDateTime.now())
                .responseData(customPage)
                .build();
    }

    @DeleteMapping
    public ApiResponse deleteReceiver(@RequestParam(required = false) String id,
                                      @RequestParam(required = false) String address,
                                      @RequestParam(required = false) String receiverName,
                                      @RequestParam(required = false) String receiverPhone,
                                      @RequestParam(required = false) String userId) {
        return ApiResponse.builder()
                .message("Delete receiver")
                .timeStamp(LocalDateTime.now())
                .message(receiverService.deleteReceiver(id, address, receiverName, receiverPhone, userId) ? "success" : "fail")
                .build();
    }
}
