package ilya.project.telegrambot.commands;

import ch.qos.logback.core.util.FileUtil;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

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
        InputFile sticker = getInputFile("rus_baical.png");
        SendSticker tgSticker = new SendSticker();
        tgSticker.setChatId(String.valueOf(chatId));
        tgSticker.setSticker(sticker);
        try {
            sender.execute(tgSticker);
        } catch (TelegramApiException e) {
            log.error("Fail to send sticker for chat with chat id = {}", chatId);
        }
    }

    private InputFile getInputFile(String fileName) {
        try {
            File file = new File(Objects.requireNonNull(FileUtil.class.getClassLoader().getResource(fileName)).toURI());
            return new InputFile(file);
        } catch (URISyntaxException | NullPointerException e) {
            log.error("File not found!");
            throw new RuntimeException(e);
        }
    }

}
