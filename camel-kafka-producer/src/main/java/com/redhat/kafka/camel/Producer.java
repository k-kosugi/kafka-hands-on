package com.redhat.kafka.camel;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class Producer extends RouteBuilder {

    private static final Logger LOG = Logger.getLogger(Producer.class);

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=1000&repeatCount=8")
                .routePolicy(new MyRoutePolicy())   // header に格納される counter をインクリメントするためのポリシー
                .setBody(simple("message" + "${exchangeProperty.counter}"))
                .log("${body}")
                .multicast().parallelProcessing()
                .to("kafka:myTopic")
                .to("kafka:myTopic")
                .to("kafka:myTopic");
    }

}
