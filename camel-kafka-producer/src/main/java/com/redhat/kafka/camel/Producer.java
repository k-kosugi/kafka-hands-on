package com.redhat.kafka.camel;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class Producer extends RouteBuilder {

    private static final Logger LOG = Logger.getLogger(Producer.class);

    private int count = 0;

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=1000&repeatCount=8")
                .routePolicy(new MyRoutePolicy())   // header に格納される counter をインクリメントするためのポリシー
                .setBody(simple("message" + "${exchangeProperty.counter}"))
                .process(exchange -> {
                    LOG.info("--------------------------------------------------");
                    var maps = exchange.getProperties();
                    var iterator = maps.entrySet().iterator();
                    while (iterator.hasNext()) {
                        var entry = iterator.next();
                        LOG.info(entry.getKey() + " : " + entry.getValue());
                    }
                    LOG.info(exchange.getIn().getBody(String.class));
                })
                .to("kafka:myTopic");
    }

}
