package ilya.project.telegrambot.bot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;

@Component
@Slf4j
public class TelegramMessageDispatcher {

    private final List<TelegramMessageHandler> handlers;

    public TelegramMessageDispatcher(List<TelegramMessageHandler> commandSet) {
        this.handlers = commandSet.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    public void handleCommands(Update update, AbsSender sender) {
        for (var handler : handlers) {
            if (handler.canHandle(update)) {
                handler.handle(update, sender);
                return;
            }
        }
        onUnknownCommand(update.getMessage().getChatId(), sender);
    }

    private void onUnknownCommand(long chatId, AbsSender sender) {
        var msg = new SendMessage(String.valueOf(chatId), "This command doesn't supported");
        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            log.error("Telegram doesn't support method message can't send in chat");
            throw new RuntimeException(e);
        }
    }

}
