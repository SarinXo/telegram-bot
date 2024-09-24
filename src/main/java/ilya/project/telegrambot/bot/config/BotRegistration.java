package ilya.project.telegrambot.bot.config;

import ilya.project.telegrambot.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
@Slf4j
public class BotRegistration {
    private final TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            log.debug("Re-init Telegram bot");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            log.debug("Bot was successfully re-registered");
        } catch (TelegramApiException e) {
            log.error("error with re-register Telegram bot!");
            log.error(e.getMessage());
        }
    }

}
