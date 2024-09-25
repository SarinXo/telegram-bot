package ilya.project.telegrambot.bot;

import ilya.project.telegrambot.bot.handlers.TelegramMessageDispatcher;
import ilya.project.telegrambot.bot.config.BotProperties;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final TelegramMessageDispatcher telegramMessageDispatcher;

    @Override
    public void onUpdateReceived(@Nonnull Update update) {
        telegramMessageDispatcher.handleCommands(update, this);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
        log.info("Bot was registered");
    }
}
