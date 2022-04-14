package epam.project.app.infra.config.app;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class PropertyLoader {
    @Getter
    private final Map<String, String> configs;

    public PropertyLoader() {
        configs = new HashMap<>();
    }

    public void loadConfig(String pathToConfigFile) {
        Properties properties = new Properties();
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(pathToConfigFile);
        try {
            properties.load(resourceAsStream);
            properties.stringPropertyNames().stream()
                    .iterator()
                    .forEachRemaining(name -> configs.put(name, properties.getProperty(name)));
        } catch (IOException e) {
            log.error("Cannot load properties. ", e);
        }
    }
}
