package ua.lg.karazhanov;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.TimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.lg.karazhanov.configuration.RestController;

import java.util.Collection;

@Slf4j
@Component
public class ServerVerticle extends AbstractVerticle {

    @Value("${vert.x.server.request.timeout}")
    private long timeout;
    @Value("${vert.x.server.port}")
    private int serverPort;

    @Autowired
    private Collection<RestController> controllers;

    private Handler<RoutingContext> pageErrorHandler;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("Startup server...");
        HttpServer httpServer = vertx.createHttpServer();
        //create routes
        Router router = Router.router(vertx);
        router.exceptionHandler(e -> log.error("Unhandled exception while routing", e));
        //request timeout
        router.route().handler(TimeoutHandler.create(timeout));
        //static route
        router.route().handler(this::handleStatic);
        //body handler for forms and uploads
        router.route().handler(BodyHandler.create());
        //cookie decode
        router.route().handler(CookieHandler.create());
//        preFilter.forEach(filter -> startFilter(router, filter));
        //add controller routes;
        startControllers(router);
//        postFilter.forEach(filter -> startFilter(router, filter));
        router.route().handler(rc -> {
            //no response was wrote
            if (!rc.request().response().ended()) {
                rc.fail(HttpResponseStatus.NOT_FOUND.code());
            }
        }).failureHandler(pageErrorHandler);

        httpServer
                .requestHandler(router::accept)
                .listen(serverPort, event -> {
                    if (event.succeeded()) {
                        log.info("Server started. Listening port: " + serverPort);
//                        if (restApiPostProcessor != null) {
//                            restApiPostProcessor.start();
//                        }
                        startFuture.complete();
                    } else {
                        log.error("Fail to start server. Listening port: " + serverPort, event.cause());
                        startFuture.fail(event.cause());
                    }
                });
    }

    private void startControllers(Router router) {
        if(controllers != null) {
            controllers.forEach(restController -> {
                log.info(restController.toString());
            });
        }
//        router.post("").
    }

    private void handleStatic(RoutingContext routingContext) {

    }
}
