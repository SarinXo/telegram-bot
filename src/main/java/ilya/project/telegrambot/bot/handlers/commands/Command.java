package ilya.project.telegrambot.bot.handlers.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface Command {
    String commandName();

    void apply(Update update, AbsSender sender);
}