package ilya.project.telegrambot.ai.prompts;


import static ilya.project.telegrambot.ai.prompts.PromptVariables.MESSAGE;
import static ilya.project.telegrambot.ai.prompts.PromptVariables.USER_NAME;

public class MessageTemplates {

    public static final String TALK_TEMPLATE =
            "Ответь на это сообщение вежливо на том языке, " +
                    "на котором оно было написано. Вот сообщение '${" + MESSAGE + "}', " +
                    "если ты хочешь обратиться к автору, то ${" + USER_NAME + "} его имя. " +
                    "Твой ответ должен касаться строго ТОЛЬКО сообщения.";

    public static final String USE_MARKDOWN =
            "Если захочешь что-то выделить особым образом, то форматируй сообщение в стиле Markdown";

    public static final String RUS =
            "Напиши небольшую фразу для поднятия духа русов! " +
                    "Чтобы они победили в войне с ящерами! " +
                    "Можешь использовать НЕ ОБЯЗАТЕЛЬНО следующие словосочетания: 'вода Байкальская', " +
                    "'К оружию', 'ящеры будут уничтожены', 'как детей'." +
                    "Если используешь сочетания, то не забудь изменить падеж";

}
