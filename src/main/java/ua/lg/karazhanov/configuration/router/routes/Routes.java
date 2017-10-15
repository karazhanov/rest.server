package ua.lg.karazhanov.configuration.router.routes;

import ua.lg.karazhanov.configuration.annotations.DELETE;
import ua.lg.karazhanov.configuration.annotations.GET;
import ua.lg.karazhanov.configuration.annotations.POST;
import ua.lg.karazhanov.configuration.annotations.PUT;
import ua.lg.karazhanov.configuration.rest.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class Routes {
    private Collection<Route> _get;
    private Collection<Route> _post;
    private Collection<Route> _put;
    private Collection<Route> _delete;

    public Routes() {
        _get = new ArrayList<>();
        _post = new ArrayList<>();
        _put = new ArrayList<>();
        _delete = new ArrayList<>();
    }

    public Collection<Route> _get() {
        return _get;
    }

    public Collection<Route> _post() {
        return _post;
    }

    public Collection<Route> _put() {
        return _put;
    }

    public Collection<Route> _delete() {
        return _delete;
    }

    public void addRoute(Annotation annotation, Method controllerMethod, RestController restController) {
        RouteHandler handler = new RouteHandler(controllerMethod, restController);
        if (annotation instanceof GET) {
            _get.add(new Route(((GET) annotation).value(), handler));
        } else
        if (annotation instanceof POST) {
            _post.add(new Route(((POST) annotation).value(), handler));
        } else
        if (annotation instanceof PUT) {
            _put.add(new Route(((PUT) annotation).value(), handler));
        } else
        if (annotation instanceof DELETE) {
            _delete.add(new Route(((DELETE) annotation).value(), handler));
        }
    }
}
