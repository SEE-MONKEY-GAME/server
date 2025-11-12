package com.seemonkey.bananajump.feedback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seemonkey.bananajump.common.response.BaseResponse;
import com.seemonkey.bananajump.common.response.MemberId;
import com.seemonkey.bananajump.feedback.dto.SendFeedbackReqDto;
import com.seemonkey.bananajump.feedback.service.FeedbackService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> sendFeedback(
        @Valid @RequestBody SendFeedbackReqDto request,
        @MemberId Long memberId
    ) {

        feedbackService.sendFeedbackToDiscord(memberId, request);

        return BaseResponse.ok();
    }
}
