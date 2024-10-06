package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.bot.service.StoreService;
import ilya.project.telegrambot.utils.TelegramRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class BuyLaptiCommand implements Command {
    private final StoreService service;

    @Override
    public String commandName() {
        return "/buy_lapti";
    }

    @Override
    public void apply(Update update, AbsSender sender) {
        long chatId = update.getMessage().getChatId();
        service.addToCart(chatId, 4L);

        TelegramRequestUtils
                .sendMessage(chatId, "Вы добавили лапти в заказ", sender);
    }
}
