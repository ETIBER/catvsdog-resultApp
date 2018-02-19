package server.route.result;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RouteResult {

    private final String BASE = "/api";
    private final String VERSION = "/v1";
    private final String RESULT_SERVICE = "/vote-percentages";

    public void createRouteResult(HttpServer httpServer, HttpHandler resultByDateHandler) {
        httpServer.createContext(BASE + VERSION + RESULT_SERVICE, resultByDateHandler);
        System.out.println("Route created on " +BASE + VERSION + RESULT_SERVICE);
    }
}
