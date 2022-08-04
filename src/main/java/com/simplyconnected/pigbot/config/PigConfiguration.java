package com.simplyconnected.pigbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@Configuration
@Validated
@ConfigurationProperties(prefix = "pig")
@Data
public class PigConfiguration {
    @NotBlank
    private String botToken = "";

    private long defaultWeight = 7;
    private double probabilityNegative = 0.19;
    private double probabilityZero = 0.01;
}
