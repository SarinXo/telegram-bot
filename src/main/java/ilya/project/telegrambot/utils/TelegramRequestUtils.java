package ilya.project.telegrambot.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramRequestUtils {

    public static boolean isCommand(Update update) {
        if(!update.getMessage().hasText()) {
            return false;
        }
        String text = update.getMessage().getText();
        return text.charAt(0) == '/';
    }
}
