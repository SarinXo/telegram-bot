package ilya.project.telegrambot.bot.service;

import ilya.project.telegrambot.bot.model.UserMessageDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.HashMap;
import java.util.Map;

import static ilya.project.telegrambot.ai.prompts.MessageTemplates.TALK_TEMPLATE;
import static ilya.project.telegrambot.ai.prompts.MessageTemplates.USE_MARKDOWN;
import static ilya.project.telegrambot.ai.prompts.PromptVariables.MESSAGE;
import static ilya.project.telegrambot.ai.prompts.PromptVariables.USER_NAME;

@Service
@RequiredArgsConstructor
public class BotMessageAiService {
    private final MistralAiChatModel chatModel;

    public SendMessage handleMessage(UserMessageDto message) {
        Map<String, String> vars = new HashMap<>();
        vars.put(MESSAGE, message.text());
        vars.put(USER_NAME, message.userName());

        StringSubstitutor sub = new StringSubstitutor(vars);
        String userMessage = sub.replace(TALK_TEMPLATE);
        Message content = new UserMessage(userMessage);
        Message instructions = new SystemMessage(USE_MARKDOWN);

        String aiResponse = chatModel.call(content, instructions);

        String chatId = Long.toString(message.chatId());
        SendMessage response = new SendMessage(chatId, aiResponse);
        response.enableMarkdown(true);
        return response;
    }

}
