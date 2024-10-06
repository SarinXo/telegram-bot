package ilya.project.telegrambot.bot.handlers;

import ilya.project.telegrambot.bot.model.UserMessageDto;
import ilya.project.telegrambot.bot.service.BotMessageAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class AiTalkHandler implements TelegramMessageHandler {

    private final BotMessageAiService botMessageAiService;

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().hasText();
    }

    @Override
    public void handle(Update update, AbsSender sender) {
        try {
            UserMessageDto dto = new UserMessageDto(
                    update.getMessage().getChatId(),
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getText()
            );
            SendMessage response = botMessageAiService.handleMessage(dto);
            sender.execute(response);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
