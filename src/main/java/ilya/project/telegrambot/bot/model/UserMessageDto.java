package ilya.project.telegrambot.bot.model;

public record UserMessageDto(
        long chatId,
        String userName,
        String text
) {
}
