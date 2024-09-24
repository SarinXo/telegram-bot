package ilya.project.telegrambot.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface CommandChain {
    void next(CommandChain next);

    void apply(Update update, AbsSender sender);
}
