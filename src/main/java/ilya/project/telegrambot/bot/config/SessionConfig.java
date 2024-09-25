package ilya.project.telegrambot.bot.config;

import ilya.project.telegrambot.bot.UserStatus;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SessionConfig {

    public final static Map<Long, UserStatus> statusMap = new ConcurrentHashMap<>();
}
