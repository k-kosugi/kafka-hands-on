package com.redhat.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MyConfiguration {

    private static final Logger LOGGER = Logger.getLogger(MyConfiguration.class);

    @Named("kafka")
    public KafkaComponent getKafkaComponent() {
        LOGGER.info("==============================================");

        // kafka component
        var kafkaConfiguration = new KafkaConfiguration();

        // リトライ回数
        kafkaConfiguration.setBrokers("localhost:9092");
        kafkaConfiguration.setRetries(5);
        kafkaConfiguration.setAutoCommitEnable(true);
        kafkaConfiguration.setBatching(false);
        kafkaConfiguration.setConsumersCount(2);

        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaConfiguration);

        return kafkaComponent;
    }
}
