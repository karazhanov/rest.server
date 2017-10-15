package ua.lg.karazhanov.configuration.rest;

import io.vertx.ext.web.RoutingContext;
import java.util.concurrent.Future;

public class RestMethodFuture<T> {

    private final String path;

    public RestMethodFuture(String path) {
        this.path = path;
    }

    public Future<T> apply(RoutingContext routingContext) {
        return null;
    }
}
