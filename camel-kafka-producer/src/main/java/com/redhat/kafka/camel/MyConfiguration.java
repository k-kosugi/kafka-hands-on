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

        /*
         * プロデューサーが、リクエストが完了したと見なす前にリーダーが受信する必要がある確認応答の数。
         * これは、送信されるレコードの耐久性を制御します。次の設定が許可されています:
         * acks=0 ゼロに設定すると、プロデューサーはサーバーからの確認応答をまったく待機しません。
         * レコードはすぐにソケット バッファーに追加され、送信されたと見なします。
         * この場合、サーバーがレコードを受信したという保証はなく、再試行構成は有効になりません (クライアントは通常、失敗を認識しないため)。
         * 各レコードに返されるオフセットは常に -1 に設定されます。
         * acks=1 これは、リーダーがレコードをローカル ログに書き込みますが、すべてのフォロワーからの完全な確認応答を待たずに応答することを意味します。
         * この場合、リーダーがレコードを確認応答した直後に、フォロワーがそれをレプリケートする前に失敗すると、レコードは失われます。
         * acks=all これは、リーダーが、同期レプリカの完全なセットがレコードを確認するまで待機することを意味します。
         * これにより、少なくとも 1 つの同期レプリカが存続している限り、レコードが失われないことが保証されます。
         * これは、利用可能な最も強力な保証です。これは、acks=-1 設定に相当します。
         * べき等性を有効にするには、この構成値を 'all' にする必要があることに注意してください。
         * 競合する構成が設定され、べき等性が明示的に有効になっていない場合、べき等性は無効になります。
         *
         * 列挙値:
         */
        kafkaConfiguration.setRequestRequiredAcks("all");

        // 冪等性
        kafkaConfiguration.setEnableIdempotence(true);

        // バッチサイズ
        kafkaConfiguration.setProducerBatchSize(0);

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
