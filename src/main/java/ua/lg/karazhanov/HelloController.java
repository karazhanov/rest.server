package ua.lg.karazhanov;

import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;
import rx.Observable;
import ua.lg.karazhanov.configuration.annotations.POST;
import ua.lg.karazhanov.configuration.rest.RestController;
import ua.lg.karazhanov.configuration.annotations.GET;

import java.util.concurrent.Future;

@Component
public class HelloController extends RestController {

    @GET("/hello")
    public Observable<?> observable(RoutingContext routingContext   ) {
        return Observable.just("HELLO");
    }

    @POST("/{id}")
    public Future<?> future() {
        return null;
    }

    @POST("/{id}")
    public void aVoid() {
    }


}
