package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.bot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class ShowCartCommand implements Command {
    private final StoreService service;


    @Override
    public String commandName() {
        return "/cart";
    }

    @Override
    public void apply(Update update, AbsSender sender) {
        Long chatId = update.getMessage().getChatId();
        var userCart = service.getCard(chatId);
        var sb = new StringBuilder();
        sb.append("Вы заказали:\n");
        userCart.forEach(
                (product, count) ->
                        sb
                                .append(product.title()).append("  -  ")
                                .append(count).append(" шт.\n")
        );

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sb.toString());
    }
}
