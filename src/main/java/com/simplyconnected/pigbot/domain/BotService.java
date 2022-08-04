package com.simplyconnected.pigbot.domain;

import com.simplyconnected.pigbot.config.PigConfiguration;
import com.simplyconnected.pigbot.model.Pig;
import com.simplyconnected.pigbot.model.User;
import com.simplyconnected.pigbot.repository.PigRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class BotService {

    private final PigRepository pigRepository;
    private final MessageProvider messageProvider;
    private final PigConfiguration pigConfiguration;
    private final Random random;
    private final Logger logger = LoggerFactory.getLogger(BotService.class);
    private final UserService userService;


    public String grow(UserCredential userCredential) {
        User user = userService.getOrRegister(userCredential);
        Pig pig = user.getPig();
        ZoneId zone = ZoneId.of("Europe/Moscow");
        ZonedDateTime curTime = Instant.now().atZone(zone);
        if (pig.getLastGrow() != null) {
            ZonedDateTime pigLastGrow = ZonedDateTime.ofInstant(pig.getLastGrow().toInstant(), zone);
            if (pigLastGrow.getDayOfYear() == curTime.getDayOfYear()
                    && Duration.between(pigLastGrow, curTime).minusDays(1).isNegative()) {
                logger.info("user={}\tpig={}\talreadyGrown=true", user.getTelegramId(), pig.getName());
                return messageProvider.alreadyGrown(pig);
            }
        }
        int diff = getDiff();
        if (pig.getWeight() + diff <= 0) {
            pig.setLastGrow(null);
            pig.setWeight(pigConfiguration.getDefaultWeight());
            pigRepository.save(pig);
            logger.info("user={}\tpig={}\tdiff={}\ttotal={}\tdied=true", user.getTelegramId(), pig.getName(), diff, pig.getWeight());
            return messageProvider.dead(pig);
        }

        pig.setWeight(pig.getWeight() + diff);
        pig.setLastGrow(OffsetDateTime.now());
        pigRepository.save(pig);
        logger.info("user={}\tpig={}\tdiff={}\ttotal={}", user.getTelegramId(), pig.getName(), diff, pig.getWeight());
        return messageProvider.diff(pig, diff);
    }

    private int getDiff() {
        double rollSign = random.nextDouble();
        double dispatch = pigConfiguration.getProbabilityNegative();
        if (rollSign < dispatch) {
            return random.nextInt(-5, 0);
        }
        dispatch += pigConfiguration.getProbabilityZero();
        if (rollSign < dispatch) {
            return 0;
        }
        return random.nextInt(0, 11);
    }

    public String rename(UserCredential userCredential, String name) {
        User user = userService.getOrRegister(userCredential);
        Pig pig = user.getPig();
        String oldName = pig.getName();

        pig.setName(name);
        pigRepository.save(pig);
        logger.info("user={}\tpig_old_name={}\tpig_new_name={}", user.getTelegramId(), oldName, pig.getName());
        return messageProvider.rename(pig);
    }

    public String top() {
        return messageProvider.top(pigRepository.findTop10ByOrderByWeightDesc());
    }
}
