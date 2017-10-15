package ua.lg.karazhanov.configuration.rest;

import io.vertx.ext.web.RoutingContext;
import rx.Observable;

public class RestMethodObservable<T> {

    private final String path;

    public RestMethodObservable(String path) {
        this.path = path;
    }

    public Observable<T> apply(RoutingContext routingContext) {
        return null;
    }
}
