package com.redhat.kafka.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.spi.RoutePolicy;

import java.util.concurrent.atomic.AtomicInteger;

public class MyRoutePolicy implements RoutePolicy {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void onExchangeBegin(Route route, Exchange exchange) {
        // Camel Route の中で message# の # 部分をインクリメントするため
        int value = counter.incrementAndGet();
        exchange.getIn().setHeader("counter", value);
    }

    @Override
    public void onInit(Route route) {

    }

    @Override
    public void onRemove(Route route) {

    }

    @Override
    public void onStart(Route route) {

    }

    @Override
    public void onStop(Route route) {

    }

    @Override
    public void onSuspend(Route route) {

    }

    @Override
    public void onResume(Route route) {

    }

    @Override
    public void onExchangeDone(Route route, Exchange exchange) {

    }
}
