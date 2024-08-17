package ru.stepup.payservise.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

// читаем из блока application.yml "integrations: executors:"
@ConfigurationProperties(prefix = "integrations.executors")
public class IntegrationsProperties {

    private final StructureIntegrationsProperties paymentsExecutorClient;

    @ConstructorBinding
    public IntegrationsProperties(StructureIntegrationsProperties paymentsExecutorClient) {
        // !!! название поля "paymentsExecutorClient" должно соответствовать ноду в yml "payments-executor-client:" !!!
        this.paymentsExecutorClient = paymentsExecutorClient;
    }

    public StructureIntegrationsProperties getPaymentsExecutorClient() {
        return paymentsExecutorClient;
    }
}
