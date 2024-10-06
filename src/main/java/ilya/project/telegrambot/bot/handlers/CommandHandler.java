package ilya.project.telegrambot.bot.handlers;

import ilya.project.telegrambot.bot.handlers.commands.Command;
import ilya.project.telegrambot.utils.TelegramRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommandHandler implements TelegramMessageHandler {

    private final Map<String, Command> commands;

    public CommandHandler(Set<Command> commandSet) {
        this.commands = commandSet.stream().collect(
                Collectors.toMap(
                        Command::commandName,
                        Function.identity()
                )
        );
    }

    @Override
    public void handle(Update update, AbsSender sender) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        Command commandHandler = commands.get(command);
        if (commandHandler != null) {
            commandHandler.apply(update, sender);
        } else {
            onUnknownCommand(chatId, sender);
        }
    }

    private void onUnknownCommand(long chatId, AbsSender sender) {
        var msg = new SendMessage(String.valueOf(chatId), "This command doesn't supported");
        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            log.error("Telegram doesn't support method message can't send in chat");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public boolean canHandle(Update update) {
        return TelegramRequestUtils.isCommand(update);
    }
}
