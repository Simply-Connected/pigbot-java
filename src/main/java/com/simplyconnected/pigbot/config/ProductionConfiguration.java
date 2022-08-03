package com.simplyconnected.pigbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ProductionConfiguration {

    @Bean
    Random random() {
        return new Random();
    }
}
