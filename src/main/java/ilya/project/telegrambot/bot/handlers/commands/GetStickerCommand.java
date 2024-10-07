package ilya.project.telegrambot.bot.handlers.commands;

import ilya.project.telegrambot.bot.service.BotMessageAiService;
import ilya.project.telegrambot.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;
import java.util.random.RandomGenerator;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetStickerCommand implements Command {

    private final BotMessageAiService botMessageAiService;

    @Override
    public String commandName() {
        return "/rus-sticker";
    }

    @Override
    public void apply(Update update, AbsSender sender) {
        long chatId = update.getMessage().getChatId();
        int rand = Random.from(RandomGenerator.getDefault()).nextInt(0, 3);
        InputFile sticker;
        switch (rand) {
            case 0 -> sticker = FileUtils.getInputFile("rus_baical.png");
            case 1 -> sticker = FileUtils.getInputFile("rus_drevniy.png");
            case 2 -> sticker = FileUtils.getInputFile("rus_god.png");
            default -> sticker = FileUtils.getInputFile("rus_baical.png");
        }

        SendSticker tgSticker = new SendSticker();
        tgSticker.setChatId(String.valueOf(chatId));
        tgSticker.setSticker(sticker);
        SendMessage messageWithSticker = botMessageAiService.rusGen(chatId);
        try {
            sender.execute(tgSticker);
            sender.execute(messageWithSticker);
        } catch (TelegramApiException e) {
            log.error("Fail to send sticker for chat with chat id = {}", chatId);
        }
    }

}
