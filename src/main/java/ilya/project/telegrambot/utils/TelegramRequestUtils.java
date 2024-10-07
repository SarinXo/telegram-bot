package ilya.project.telegrambot.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramRequestUtils {

    public static boolean isCommand(Update update) {
        if (!update.getMessage().hasText()) {
            return false;
        }
        String text = update.getMessage().getText();
        return text.charAt(0) == '/';
    }

    public static void sendMessage(Long chatId, String msg, AbsSender sender) {
        sendMessage(String.valueOf(chatId), msg, sender);
    }

    public static void sendMessage(String chatId, String msg, AbsSender sender) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(msg);

        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
