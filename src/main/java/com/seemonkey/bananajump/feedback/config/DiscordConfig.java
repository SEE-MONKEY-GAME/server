package com.seemonkey.bananajump.feedback.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class DiscordConfig {

    @Value("${discord.webhook-url}")
    private String webhookUrl;
}
