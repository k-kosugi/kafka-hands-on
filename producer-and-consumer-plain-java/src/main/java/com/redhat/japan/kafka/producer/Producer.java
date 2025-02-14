package com.redhat.japan.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Producer {

    private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());

    public static void main(String[] args) {
        // KafkaProducer の生成
        var properties = getProperties();

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 同期送信
        Stream.of("message1", "message2", "message3", "message4", "message5", "message6", "message7", "message8")
                .forEach(i -> {
                    try {
                        // Record の生成(キーなしでレコードを生成)
                        // 第一引数は topic 名
                        // 第二引数は メッセージ
                        ProducerRecord<String, String> records = new ProducerRecord<>("myTopic", i);

                        // key-value で送信したい場合は↑をコメントアウトし、以下のコメントアウトを外す
//                        var key = UUID.randomUUID().toString();
//                        ProducerRecord<String, String> records = new ProducerRecord<>("myTopic", key, i);

                        Future<RecordMetadata> future = producer.send(records);
                        RecordMetadata recordMetadata = future.get();

                        System.out.println("=====================================================");
                        System.out.println("              topic : " + recordMetadata.topic());
                        System.out.println("              value : " + i);
                        System.out.println("          partition : " + recordMetadata.partition());
                        System.out.println("             offset : " + recordMetadata.offset());
                        System.out.println("       hasTimestamp : " + recordMetadata.hasTimestamp());
                        System.out.println("          timestamp : " + recordMetadata.timestamp());
                        System.out.println("          hasOffset : " + recordMetadata.hasOffset());
                        System.out.println("  serializedKeySize : " + recordMetadata.serializedKeySize());
                        System.out.println("serializedValueSize : " + recordMetadata.serializedValueSize());

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static Properties getProperties() {
        // Properties の生成
        Properties properties = new Properties();

        // Kafka Broker の設定
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");//

        // StringSerializer の設定
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        /*
         * Producer 側はべき等性を設定することが可能
         */
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        /*
         * 0	Producer が Message を送信時、Ack を待たずに次の Message を送信する
         * 1	Leader の Replica に書き込まれたら Ack を返す
         * all	すべての In Sync Replica 数まで書き込まれたら Ack を返す
         */
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");

        /*
         * Kafka はメッセージの送信時に1つ1つのメッセージ単位で送るわけではなく、送受信のスループットを上げるためにある程度メッセージを蓄積して
         * バッチ処理で送受信する機能を備えています。
         *
         */
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16");
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1");

        return properties;
    }

}