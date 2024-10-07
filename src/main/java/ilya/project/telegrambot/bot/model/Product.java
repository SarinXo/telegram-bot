package ilya.project.telegrambot.bot.model;

import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.math.BigDecimal;

public record Product(
        String title,
        String engName,
        BigDecimal price,
        InputFile picture
) {
}
