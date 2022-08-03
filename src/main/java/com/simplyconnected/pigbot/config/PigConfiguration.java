package com.simplyconnected.pigbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pig")
@Data
public class PigConfiguration {
    private String botToken;
    private long defaultWeight;
}
