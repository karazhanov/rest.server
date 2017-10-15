package ua.lg.karazhanov.configuration.router.routes;

public class Route {
    private String path;
    private RouteHandler handler;

    Route(String path, RouteHandler handler) {
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public RouteHandler getHandler() {
        return handler;
    }

}
