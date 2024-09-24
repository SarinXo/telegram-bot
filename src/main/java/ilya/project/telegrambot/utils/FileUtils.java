package ilya.project.telegrambot.utils;

import ch.qos.logback.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

@Slf4j
public class FileUtils {

    public static InputFile getInputFile(String fileName) {
        try {
            File file = new File(Objects.requireNonNull(FileUtil.class.getClassLoader().getResource(fileName)).toURI());
            return new InputFile(file);
        } catch (URISyntaxException | NullPointerException e) {
            log.error("File not found!");
            throw new RuntimeException(e);
        }
    }
}
