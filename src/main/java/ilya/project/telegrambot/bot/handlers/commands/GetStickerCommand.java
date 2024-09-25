package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetStickerCommand implements Command {

    @Override
    public String commandName() {
        return "/sticker";
    }

    @Override
    public void apply(Update update, AbsSender sender) {
        long chatId = update.getMessage().getChatId();
        InputFile sticker = FileUtils.getInputFile("rus_baical.png");

        SendSticker tgSticker = new SendSticker();
        tgSticker.setChatId(String.valueOf(chatId));
        tgSticker.setSticker(sticker);
        try {
            sender.execute(tgSticker);
        } catch (TelegramApiException e) {
            log.error("Fail to send sticker for chat with chat id = {}", chatId);
        }
    }

}
