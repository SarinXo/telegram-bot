package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.bot.service.StoreService;
import ilya.project.telegrambot.utils.TelegramRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

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
        var userCart = Optional.ofNullable(service.getCard(chatId)).orElseGet(HashMap::new);
        var sb = new StringBuilder()
                .append("Вы заказали:\n");
        BigDecimal cost = userCart.entrySet().stream()
                .peek(
                        entry -> {
                            var product = entry.getKey();
                            var count = entry.getValue();
                            sb
                                    .append(product.title()).append("  -  ")
                                    .append(count).append(" шт.\t\t")
                                    .append("цена за 1 шт товара ").append(product.price()).append("\n");
                        })
                .map(
                        entry -> {
                            var product = entry.getKey();
                            var count = entry.getValue();
                            return product.price().multiply(BigDecimal.valueOf(count));
                        })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sb.append("Итого с вас: ").append(cost).append(" руб");
        TelegramRequestUtils.sendMessage(chatId, sb.toString(), sender);
    }
}
