package ilya.project.telegrambot.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface TelegramMessageHandler extends Comparable<TelegramMessageHandler>{
    int priority();
    boolean canHandle(Update update);
    void handle(Update update, AbsSender sender);

    @Override
    default int compareTo(TelegramMessageHandler o) {
        return Integer.compare(priority(), o.priority());
    }
}
