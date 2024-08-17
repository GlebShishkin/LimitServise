package ru.stepup.payservise.config.properties;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

// структура для чтения блока "integrations: executors:"
@Getter
@Setter
public class StructureIntegrationsProperties {
    // соответствует структуре и названиям полей в yml блоке "integrations: executors: payments-executor-client:"
    private String url;
    private Duration connectTimeout;
    private Duration readTimeout;
}
