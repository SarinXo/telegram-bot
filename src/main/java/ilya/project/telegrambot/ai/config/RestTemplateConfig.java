package ilya.project.telegrambot.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestTemplateConfig {
    /**
     * Для MistralAiChatModel
     */
    @Bean
    public RestClient.Builder restTemplate() {
        return RestClient.builder();
    }
}
