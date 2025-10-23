package com.seemonkey.bananajump.feedback.service;


import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.feedback.config.DiscordConfig;
import com.seemonkey.bananajump.feedback.dto.SendFeedbackReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpMethod.POST;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final DiscordConfig discordConfig;

    @Override
    public void sendFeedbackToDiscord(Long memberId, SendFeedbackReqDto dto){

        // 1) 작성 시간 포맷 변환
        String formattedTime = dto.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 2) Discord에 보낼 메시지 내용 구성
        String messageContent = new StringBuilder()
                .append("**🗣️ 새 건의사항 도착!**\n")
                .append("👤 **Member ID:** ").append(memberId).append("\n")
                .append("💬 **내용:** ").append(dto.getContent()).append("\n")
                .append("🕒 **작성시각:** ").append(formattedTime).append(" (KST)")
                .toString();

        // 3) Discord에 보낼 양식 구성
        Map<String, Object> payload = Map.of("content", messageContent);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json; charset=UTF-8"));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        // 4) Discord Webhook 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                discordConfig.getWebhookUrl(),
                POST,
                entity,
                String.class
        );


        Optional.ofNullable(response)
                .filter(r -> r.getStatusCode().is2xxSuccessful())
                .orElseThrow(() -> new CustomException(ErrorType.DISCORD_SEND_FAILED));
    }

}
