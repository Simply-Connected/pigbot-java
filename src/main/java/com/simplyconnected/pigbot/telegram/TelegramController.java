package com.simplyconnected.pigbot.telegram;

import com.simplyconnected.pigbot.config.PigConfiguration;
import com.simplyconnected.pigbot.domain.UserCredential;
import com.simplyconnected.pigbot.domain.BotService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.toggle.BareboneToggle;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class TelegramController extends AbilityBot {
    private static final BareboneToggle toggle = new BareboneToggle();
    private final BotService botService;

    public TelegramController(BotService botService, PigConfiguration pigConfiguration) {
        super(pigConfiguration.getBotToken(), "pig1411_bot", toggle);
        this.botService = botService;
    }

    @EventListener
    public void initBot(ContextRefreshedEvent ctx) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public long creatorId() {
        return 390828910;
    }

    public Ability grow() {
        return Ability
                .builder()
                .name("grow")
                .info("Grow your pig")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.sendMd(botService.grow(
                        new UserCredential(ctx.user().getId(), ctx.user().getUserName())), ctx.chatId()))
                .build();
    }

    public Ability rename() {
        return Ability
                .builder()
                .name("rename")
                .info("Rename your pig")
                .input(1)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.sendMd(botService.rename(
                        new UserCredential(ctx.user().getId(), ctx.user().getUserName()), ctx.firstArg()), ctx.chatId()))
                .build();
    }

    public Ability top() {
        return Ability
                .builder()
                .name("top")
                .info("Top 10 pigs")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.sendMd(botService.top(), ctx.chatId()))
                .build();
    }
}
