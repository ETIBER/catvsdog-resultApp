package server;

import com.sun.net.httpserver.HttpServer;
import factories.HttpServerFactory;
import server.route.result.ResultByDateHandler;
import server.route.result.RouteResult;

import java.io.IOException;
import java.sql.SQLException;

public class Server {
    private static final Integer API_SERVER_PORT =
            Integer.getInteger(System.getenv("API_SERVER_PORT") != null
                    ? System.getenv("API_SERVER_PORT") : "80",80);

    private HttpServerFactory httpServerFactory;
    private RouteResult routeResult;

    public Server() {
        httpServerFactory = new HttpServerFactory();
        routeResult = new RouteResult();
    }


    public void createServer() throws IOException, SQLException {
        HttpServer httpServer = httpServerFactory.createHttpServer(API_SERVER_PORT);
        routeResult.createRouteResult(httpServer,new ResultByDateHandler());
        httpServer.start();
        System.out.println("Server listing in: "+API_SERVER_PORT);
    }
}
