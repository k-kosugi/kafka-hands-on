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
        kafkaConfiguration.setAllowManualCommit(false);
        kafkaConfiguration.setBatching(false);
        kafkaConfiguration.setMaxPollRecords(1);

        // Consumer のスレッド数
        kafkaConfiguration.setConsumersCount(4);

        // 先頭から読み出す
        kafkaConfiguration.setAutoOffsetReset("earliest");
        kafkaConfiguration.setGroupId("group1");

        kafkaConfiguration.setKeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConfiguration.setValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer");

        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaConfiguration);

        return kafkaComponent;
    }
}
