package com.seemonkey.bananajump.feedback.service;

import com.seemonkey.bananajump.feedback.dto.SendFeedbackReqDto;

public interface FeedbackService {
    void sendFeedbackToDiscord(Long memberId, SendFeedbackReqDto dto);
}
