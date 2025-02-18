package com.redhat.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.Logger;

public class Consumer extends RouteBuilder {

    private static final Logger LOG = Logger.getLogger(Consumer.class);

    /**
     * Camel Route
     * @throws Exception
     */
    public void configure() throws Exception {
        from("kafka:myTopic")
                .routeId("kafka-consumer")
                .process(exchange -> {
                    LOG.info("--------------------------------------------------");
                    // header の取得
                    var headers = exchange.getMessage().getHeaders();
                    var iterator = headers.entrySet().iterator();
                    while (iterator.hasNext()) {
                        var entry = iterator.next();
                        LOG.info(entry.getKey() + " : " + entry.getValue());
                    }
                })
                .log("${body}");
    }
}
