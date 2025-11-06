package com.seemonkey.bananajump.feedback.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.feedback.config.NotionConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotionClient {

    private final NotionConfig notionConfig;
    private final ObjectMapper mapper = new ObjectMapper();

    public void saveFeedback(Long memberId, String content, String createdAt) {
        Map<String, Object> parent = Map.of("database_id", notionConfig.getDatabaseId());
        Map<String, Object> props = new HashMap<>();

        props.put("memberID", Map.of("title", new Object[]{
                Map.of("text", Map.of("content", String.valueOf(memberId)))
        }));
        props.put("Content", Map.of("rich_text", new Object[]{Map.of("text", Map.of("content", content))}));
        props.put("Date", Map.of("date", Map.of("start", createdAt)));

        Map<String, Object> body = Map.of("parent", parent, "properties", props);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(notionConfig.getSecretKey());
        headers.set("Notion-Version", "2022-06-28");

        ResponseEntity<String> response;
        try {
            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);
            response = new RestTemplate().postForEntity(
                    notionConfig.getApiUrl() + "/pages",
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new CustomException(ErrorType.NOTION_SEND_FAILED);
        }

        Optional.ofNullable(response)
                .filter(r -> r.getStatusCode().is2xxSuccessful())
                .orElseThrow(() -> new CustomException(ErrorType.NOTION_SEND_FAILED));
    }
}

