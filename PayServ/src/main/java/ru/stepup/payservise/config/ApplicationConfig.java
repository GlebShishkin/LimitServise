package ru.stepup.payservise.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.stepup.payservise.config.properties.ApplicationProperties;
import ru.stepup.payservise.config.properties.IntegrationsProperties;
import ru.stepup.payservise.config.properties.StructureIntegrationsProperties;

@Configuration
// читаем настройки из пропертей (yml)
@EnableConfigurationProperties({
        ApplicationProperties.class,    // service:
        IntegrationsProperties.class    // integrations: executors:
})
public class ApplicationConfig {

    // загрузим bean "restTemplate" для запросов серверу (настройки загрузки возьмем из ApplicationProperties и )
    @Bean
    public RestTemplate restTemplate(
            IntegrationsProperties integrationsProperties
            , RestTemplateResponseErrorHandler errorHandler   // привязываем обработчик ошибок RestTemplateResponseErrorHandler
        )
    {
        StructureIntegrationsProperties structureIntegrationsProperties = integrationsProperties.getPaymentsExecutorClient();
        return new RestTemplateBuilder()
                .rootUri(structureIntegrationsProperties.getUrl())
                .errorHandler(errorHandler) // привяжем обработчик ошибок RestTemplateResponseErrorHandler
                .build();
    }
}
