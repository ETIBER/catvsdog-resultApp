package server;

import com.sun.net.httpserver.HttpServer;
import factories.HttpServerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.route.result.ResultByDateHandler;
import server.route.result.RouteResult;
import services.ResultService.ResultServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class.getName());
    private static final Integer API_SERVER_PORT =
            Integer.getInteger(System.getenv("API_SERVER_PORT") != null
                    ? System.getenv("API_SERVER_PORT") : "80",80);

    private HttpServerFactory httpServerFactory;
    private RouteResult routeResult;

    public Server(HttpServerFactory httpServerFactory,RouteResult routeResult) {
        this.httpServerFactory = httpServerFactory;
        this.routeResult = routeResult;
    }


    public void createServer() throws IOException, SQLException {
        HttpServer httpServer = httpServerFactory.createHttpServer(API_SERVER_PORT);
        routeResult.createRouteResult(httpServer,new ResultByDateHandler(new ResultServiceImpl()));
        httpServer.start();
        logger.info("Server listing in: "+API_SERVER_PORT);
    }
}
