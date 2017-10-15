package ua.lg.karazhanov.configuration.router.routes;

import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import ua.lg.karazhanov.configuration.rest.RestController;

import java.lang.reflect.Method;

public class RouteHandler {
    private final Method method;
    private final RestController restController;

    public RouteHandler(Method method, RestController restController) {
        this.method = method;
        this.restController = restController;
    }

    @SneakyThrows
    public void apply(RoutingContext routingContext) {
        method.invoke(restController, routingContext);
    }
}
