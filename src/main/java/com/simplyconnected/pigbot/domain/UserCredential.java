package com.simplyconnected.pigbot.domain;

import lombok.Value;

@Value
public class UserCredential {
    Long telegramId;
    String name;
}
