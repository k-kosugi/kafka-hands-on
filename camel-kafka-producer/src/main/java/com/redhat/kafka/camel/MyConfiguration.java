package com.redhat.kafka.camel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MyConfiguration {

    private static final Logger LOG = Logger.getLogger(MyConfiguration.class);

    @Named("kafka")
    public KafkaComponent getKafkaComponent() {
        LOG.info("======================KafkaComponent========================");

        var kafkaConfiguration = new KafkaConfiguration();

        // ブローカーの設定
        kafkaConfiguration.setBrokers("localhost:9092");

        // リトライ回数
        kafkaConfiguration.setRetries(5);

        // 自動コミット
        kafkaConfiguration.setAutoCommitEnable(true);

        // バッチサイズ
        kafkaConfiguration.setProducerBatchSize(7);

        // ClientId
        kafkaConfiguration.setClientId(Producer.class.getSimpleName());

        // Keyシリアライザー
        kafkaConfiguration.setKeySerializer("org.apache.kafka.common.serialization.StringSerializer");

        // Value シリアライザー
        kafkaConfiguration.setValueSerializer("org.apache.kafka.common.serialization.StringSerializer");

        KafkaComponent kafkaComponent = new KafkaComponent();
        kafkaComponent.setConfiguration(kafkaConfiguration);

        LOG.info("======================KafkaComponent========================");
        return kafkaComponent;
    }
}
