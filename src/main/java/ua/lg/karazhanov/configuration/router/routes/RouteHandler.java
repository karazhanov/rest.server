package ua.lg.karazhanov.configuration.router.routes;

import com.google.gson.JsonElement;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import rx.Observable;
import ua.lg.karazhanov.configuration.rest.RestController;
import ua.lg.karazhanov.configuration.router.ContentTypes;

import java.lang.reflect.Method;

public class RouteHandler {
    private final Method method;
    private final RestController restController;

    RouteHandler(Method method, RestController restController) {
        this.method = method;
        this.restController = restController;
    }

    @SneakyThrows
    public void apply(RoutingContext routingContext) {
        Observable<?> invoke = (Observable) method.invoke(restController, routingContext);
        invoke.subscribe(o -> sendResult(routingContext, o),
                        throwable -> sendError(routingContext, throwable));
    }

    private void sendResult(RoutingContext rc, Object result) {
        HttpServerResponse response = rc.response();
        response.putHeader(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON_UTF_8);
        if(result instanceof Buffer) {
            response.end((Buffer) result);
        } else if(result instanceof JsonElement){
            response.end(result.toString());
        } else {
            response.end(Json.encode(result));
        }
    }

    private void sendError(RoutingContext rc, Throwable cause) {
        rc.fail(cause);
    }
}
