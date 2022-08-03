package com.simplyconnected.pigbot.domain;

import com.google.common.collect.Streams;
import com.simplyconnected.pigbot.model.Pig;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class MessageProvider {

    public String dead(@NotNull Pig pig) {
        return String.format("""
                        Ваша свинья *%s* потеряла весь вес и умерла ☠️. Вы получаете нового порося.

                        Вес вашего свина: *%d*"""
                , pig.getName(), pig.getWeight());
    }

    public String alreadyGrown(@NotNull Pig pig) {
        return String.format("""
                Вы уже кормили свою свинью сегодня.
                                
                Вес вашего свина: *%d*
                """, pig.getWeight());
    }

    public String diff(@NotNull Pig pig, int diff) {
        if (diff == 0) {
            return String.format("""
                    Ваш *%s* не нашел своего корма и лег спать.

                    Вес вашего свина: *%d*""", pig.getName(), pig.getWeight());
        } else if (diff > 0) {
            return String.format("""
                    Ваш *%s* съел морковки и поправился на *%d* килограмм.

                    Вес вашего свина: *%d*""", pig.getName(), diff, pig.getWeight());
        } else {
            return String.format("""
                    Вашему *%s* попался сгнивший корм и он потерял *%d* килограмм.

                    Вес вашего свина: *%d*""", pig.getName(), -diff, pig.getWeight());
        }
    }

    public String rename(@NotNull Pig pig) {
        return String.format("Теперь вашу свинью зовут *%s*", pig.getName());
    }

    public String top(List<Pig> pigs) {
        return Streams.concat(Stream.of("Топ *10* свинок:\n\n"),
                IntStream.range(0, pigs.size())
                        .mapToObj(i -> String.format("*%d*. *%s* - %d кг\n", i + 1,
                                pigs.get(i).getName(), pigs.get(i).getWeight())
                        )).collect(Collectors.joining(""));
    }
}
