package com.seemonkey.bananajump.feedback.service;


import com.seemonkey.bananajump.feedback.client.DiscordClient;
import com.seemonkey.bananajump.feedback.dto.SendFeedbackReqDto;
import com.seemonkey.bananajump.feedback.client.NotionClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final DiscordClient discordClient;
    private final NotionClient notionClient;

    @Override
    public void sendFeedbackToDiscord(Long memberId, SendFeedbackReqDto dto) {

        // 1) 작성 시간 포맷 변환
        String formattedTime = dto.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 2) Discord에 보낼 메시지 내용 구성
        String message = new StringBuilder()
                .append("**🗣️ 새 건의사항 도착!**\n")
                .append("👤 **Member ID:** ").append(memberId).append("\n")
                .append("💬 **내용:** ").append(dto.getContent()).append("\n")
                .append("🕒 **작성시각:** ").append(formattedTime).append(" (KST)")
                .toString();

        // 3) Discord 전송
        discordClient.sendMessage(Map.of("content", message));

        // 4) 노션에 전송
        String utcTime = dto.getCreatedAt()
                .atZone(ZoneId.of("Asia/Seoul"))
                .withZoneSameInstant(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);

        notionClient.saveFeedback(memberId, dto.getContent(), utcTime);
    }
}
