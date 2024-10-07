package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.bot.service.StoreService;
import ilya.project.telegrambot.utils.TelegramRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class BuyCapCommand implements Command {

    private final StoreService service;

    @Override
    public String commandName() {
        return "/buy_cap";
    }

    @Override
    public void apply(Update update, AbsSender sender) {
        long chatId = update.getMessage().getChatId();
        service.addToCart(chatId, 1L);

        TelegramRequestUtils.sendMessage(chatId, "Вы добавили кепку в заказ", sender);
    }
}
