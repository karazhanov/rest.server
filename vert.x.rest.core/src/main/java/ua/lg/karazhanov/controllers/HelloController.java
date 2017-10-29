package ua.lg.karazhanov.controllers;

import ua.lg.karazhanov.annotations.VertXRestController;
import ua.lg.karazhanov.annotations.methods.GET;
import ua.lg.karazhanov.annotations.methods.POST;
import ua.lg.karazhanov.annotations.methods.PUT;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;
import rx.Observable;
import ua.lg.karazhanov.annotations.params.Path;
import ua.lg.karazhanov.annotations.params.Query;
import ua.lg.karazhanov.configuration.rest.RestController;

@VertXRestController
public class HelloController extends RestController {
    public HelloController() {
        super("/test/?d");
    }

    @GET("hello/:l")
    public Observable<?> test1(@Path long lt, @Query String o) {
        return Observable.just("HELLO");
    }

    @GET(":id")
    public Observable<Object> observableID(RoutingContext routingContext, Integer in) {
        String id = routingContext.request().getParam("id");
        return Observable.just("HELLO " + id);
    }
//
//    @PUT("/:id")
//    public Observable<Object> observableID(Object routingContext) {
//        return Observable.just("HELLO ");
//    }
//
//    @POST("/bytracker/:trackerId/test?uid=:uid&test=t")
//    public Observable<?> future(RoutingContext routingContext) {
//        return null;
//    }
//
//    @POST("/{id}")
//    public void aVoid() {
//        System.out.println("HELLO");
//    }
}