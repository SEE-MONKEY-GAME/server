package com.seemonkey.bananajump.feedback.client;

import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.feedback.config.DiscordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpMethod.POST;

@Component
@RequiredArgsConstructor
public class DiscordClient {

    private final DiscordConfig discordConfig;

    public void sendMessage(Map<String, Object> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json; charset=UTF-8"));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response;
        try {
            response = new RestTemplate().exchange(
                    discordConfig.getWebhookUrl(),
                    POST,
                    entity,
                    String.class
            );
        } catch (Exception e) {
            throw new CustomException(ErrorType.DISCORD_SEND_FAILED);
        }

        Optional.ofNullable(response)
                .filter(r -> r.getStatusCode().is2xxSuccessful())
                .orElseThrow(() -> new CustomException(ErrorType.DISCORD_SEND_FAILED));
    }
}
