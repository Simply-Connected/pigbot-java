package com.simplyconnected.pigbot;

import com.simplyconnected.pigbot.config.PigConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PigConfiguration.class)
public class PigbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigbotApplication.class, args);
	}

}
