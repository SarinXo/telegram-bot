package ilya.project.telegrambot.bot;

import ilya.project.telegrambot.bot.commands.CommandsHandler;
import ilya.project.telegrambot.bot.config.BotProperties;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final CommandsHandler commandsHandler;

    @Override
    public void onUpdateReceived(@Nonnull Update update) {
        log.debug("Start processing the message");
        if (update.hasMessage() && update.getMessage().hasText()) {
            commandsHandler.handleCommands(update, this);
        } else {
            onUnknownCommand(update.getMessage().getChatId());
        }
    }

    private void onUnknownCommand(long chatId) {
        try {
            SendMessage msg = new SendMessage();
            msg.setChatId(chatId);
            msg.setText("I don't understand you");
            execute(msg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
        log.info("Bot was registered");
    }
}
