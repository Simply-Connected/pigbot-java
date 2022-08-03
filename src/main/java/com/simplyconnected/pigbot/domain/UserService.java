package com.simplyconnected.pigbot.domain;

import com.simplyconnected.pigbot.config.PigConfiguration;
import com.simplyconnected.pigbot.model.Pig;
import com.simplyconnected.pigbot.model.User;
import com.simplyconnected.pigbot.repository.PigRepository;
import com.simplyconnected.pigbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PigRepository pigRepository;
    private final PigConfiguration pigConfiguration;

    User getOrRegister(UserCredential userCredential) {
        Optional<User> user = userRepository.findByTelegramId(userCredential.getTelegramId());
        if (user.isEmpty()) {
            Pig pig = Pig.builder()
                    .name(userCredential.getName())
                    .weight(pigConfiguration.getDefaultWeight())
                    .build();
            pig.setUser(User.builder()
                    .name(userCredential.getName())
                    .telegramId(userCredential.getTelegramId())
                    .pig(pig)
                    .build());

            pigRepository.save(pig);
            return pig.getUser();
        }
        return user.get();
    }
}
