package com.redhat.kafka;

import org.apache.camel.builder.RouteBuilder;

public class Consumer extends RouteBuilder {

    public void configure() throws Exception {
        from("kafka:myTopic?groupId=myGroup")
                .routeId("kafka-consumer")
                .log("${body}");
    }
}
