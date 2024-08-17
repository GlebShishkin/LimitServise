package ru.stepup.payservise.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

// bean общих данных данных приложения
@Getter
@ConfigurationProperties(prefix = "service")
public class ApplicationProperties {

    private final String name;
    private final String family;

    public ApplicationProperties(String name, String family, String url) {
        this.name = name;
        this.family = family;
    }

    @Override
    public String toString() {
        return "ApplicationConfigProperties{" +
                "name = '" + name + '\'' +
                ", family = '" + family + '\'' +
                '}';
    }
}
