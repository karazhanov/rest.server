package ua.lg.karazhanov.controllers;

import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;
import rx.Observable;
import ua.lg.karazhanov.configuration.rest.RestController;
import ua.lg.karazhanov.configuration.rest.annotations.GET;
import ua.lg.karazhanov.configuration.rest.annotations.POST;
import ua.lg.karazhanov.configuration.rest.annotations.PUT;

@Component
public class HelloController extends RestController {

    @GET("/hello")
    public Observable<?> observable(RoutingContext routingContext) {
        return Observable.just("HELLO");
    }

    @GET("/:id")
    public Observable<Object> observableID(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        return Observable.just("HELLO " + id);
    }

    @PUT("/:id")
    public Observable<Object> observableID(Object routingContext) {
        return Observable.just("HELLO ");
    }

    @POST("/{id?  mgoiy89}")
    public Observable<?> future() {
        return null;
    }

    @POST("/{id}")
    public void aVoid() {
    }


}
