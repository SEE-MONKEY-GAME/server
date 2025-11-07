package com.seemonkey.bananajump.feedback.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class NotionConfig {

    @Value("${notion.api.url}")
    private String apiUrl;

    @Value("${notion.api.key}")
    private String secretKey;

    @Value("${notion.database.id}")
    private String databaseId;

}