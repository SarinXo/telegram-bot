package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.bot.service.StoreService;
import ilya.project.telegrambot.utils.TelegramRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowProductListCommand implements Command {

    private final StoreService service;

    @Override
    public String commandName() {
        return "/product_list";
    }

    @Override
    public void apply(Update update, AbsSender sender) {
        Long chatId = update.getMessage().getChatId();
        var userCart = service.getStoreCard();
        List<KeyboardButton> buttons = new ArrayList<>();
        userCart.forEach(
                product -> {
                    TelegramRequestUtils.sendMessage(chatId, product.title(), sender);

                    buttons.add(new KeyboardButton("/buy_" + product.engName()));

                    StringBuilder sb = new StringBuilder();
                    sb.append("Купить ").append(product.title()).append(" за ").append(product.price()).append(" руб.");

                    SendPhoto sendPhotoRequest = new SendPhoto();
                    sendPhotoRequest.setChatId(String.valueOf(chatId));
                    sendPhotoRequest.setPhoto(product.picture());
                    try {
                        sender.execute(sendPhotoRequest);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
        );

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(buttons);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        replyKeyboardMarkup.setResizeKeyboard(true);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбирайте мудро:");
        message.setReplyMarkup(replyKeyboardMarkup);
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

