package ilya.project.telegrambot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
public class CommandsHandler {

    private final Map<String, Command> commands;

    public CommandsHandler(Set<Command> commandSet) {
        this.commands = commandSet.stream().collect(
                Collectors.toMap(
                        Command::commandName,
                        Function.identity()
                )
        );
    }

    public void handleCommands(Update update, AbsSender sender) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            commandHandler.apply(update, sender);
        } else {
            var msg = new SendMessage(String.valueOf(chatId), "This command doesn't supported");
            try {
                sender.execute(msg);
            } catch (TelegramApiException e) {
                log.error("Telegram doesn't support method message can't send in chat");
                throw new RuntimeException(e);
            }
        }

    }

}
