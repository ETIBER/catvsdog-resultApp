package server;

import com.sun.net.httpserver.HttpServer;
import factories.HttpServerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import server.route.result.ResultByDateHandler;
import server.route.result.RouteResult;

import java.io.IOException;
import java.sql.SQLException;

import  static  org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest {

    private static final Integer API_SERVER_PORT =
            Integer.getInteger(System.getenv("API_SERVER_PORT") != null
                    ? System.getenv("API_SERVER_PORT") : "80",80);

    @Mock
    private HttpServerFactory httpServerFactory;
    @Mock
    private HttpServer httpServerMock;
    @Mock
    private RouteResult routeResultMock;

    @InjectMocks
    private Server server;


    @Before
    public  void setUp() {
    }

    @Test
    public void createServerCreateTheServer() throws IOException, SQLException {
        // GIVEN
        when(httpServerFactory.createHttpServer(API_SERVER_PORT)).thenReturn(httpServerMock);
        // WHEN
        server.createServer();
        // THEN
        verify(httpServerFactory).createHttpServer(API_SERVER_PORT);

    }

    @Test
    public void createServerInitializeRoute() throws IOException, SQLException {
        // GIVEN
        when(httpServerFactory.createHttpServer(API_SERVER_PORT)).thenReturn(httpServerMock);
        // WHEN
        server.createServer();
        // THEN
        verify(routeResultMock).createRouteResult(eq(httpServerMock),any(ResultByDateHandler.class));

    }

    @Test
    public void createServerStartTheServer() throws IOException, SQLException {
        // GIVEN
        when(httpServerFactory.createHttpServer(API_SERVER_PORT)).thenReturn(httpServerMock);
        // WHEN
        server.createServer();
        // THEN
        verify(httpServerMock).start();
    }
}