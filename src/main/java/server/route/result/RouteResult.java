package server.route.result;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouteResult {
    private static final Logger logger = LogManager.getLogger(" ApiConnectionResultServiceImpl");

    private final String BASE = "/api";
    private final String VERSION = "/v1";
    private final String RESULT_SERVICE = "/vote-percentages";

    public void createRouteResult(HttpServer httpServer, HttpHandler resultByDateHandler) {
        httpServer.createContext(BASE + VERSION + RESULT_SERVICE, resultByDateHandler);
        logger.info("Route created on " +BASE + VERSION + RESULT_SERVICE);
    }
}
