package com.seemonkey.bananajump.feedback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendFeedbackReqDto {

    @NotBlank
    String content;

    @PastOrPresent
    LocalDateTime createdAt;
}
