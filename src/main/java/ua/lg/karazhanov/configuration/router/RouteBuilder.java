package ua.lg.karazhanov.configuration.router;

import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import ua.lg.karazhanov.configuration.rest.RestController;
import ua.lg.karazhanov.configuration.router.exceptions.MultiplyRestMethodsException;
import ua.lg.karazhanov.configuration.router.exceptions.WrongReturnTypeException;
import ua.lg.karazhanov.configuration.router.routes.RouteValidator;
import ua.lg.karazhanov.configuration.router.routes.Routes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

@Slf4j
public class RouteBuilder {
    public static void configure(Router router, Collection<RestController> controllers) {
        if (controllers != null) {
            controllers.forEach(restController -> configRoute(router, restController));
        }
    }

    private static void configRoute(Router router, RestController restController) {
        Routes validRoutes = createValidRoutes(restController);
        addRoutes(router, validRoutes);
    }

    private static Routes createValidRoutes(RestController restController) {
        Method[] controllerMethods = restController.getClass().getDeclaredMethods();
        Routes routes = new Routes();
        for (Method controllerMethod : controllerMethods) {
            try {
                Annotation annotation = RouteValidator.isAnnotated(controllerMethod);
                if (annotation == null) {
                    continue;
                }
                RouteValidator.validateSignature(controllerMethod);
                routes.addRoute(annotation, controllerMethod, restController);
            } catch (MultiplyRestMethodsException e) {
                log.error("MultiplyRestMethodsException in " + e.getControllerMethod());
            } catch (WrongReturnTypeException e) {
                log.error("WrongReturnTypeException in " + e.getControllerMethod());
            }
        }
        return routes;
    }

    private static void addRoutes(Router router, Routes validRoutes) {
        validRoutes._get().forEach(r -> {
            log.info("GET " + r.getPath());
            router.get(r.getPath()).handler(r.getHandler()::apply);
        });
        validRoutes._post().forEach(r -> {
            log.info("POST " + r.getPath());
            router.post(r.getPath()).handler(r.getHandler()::apply);
        });
        validRoutes._put().forEach(r -> {
            log.info("PUT " + r.getPath());
            router.put(r.getPath()).handler(r.getHandler()::apply);
        });
        validRoutes._delete().forEach(r -> {
            log.info("DELETE " + r.getPath());
            router.delete(r.getPath()).handler(r.getHandler()::apply);
        });
    }

}
