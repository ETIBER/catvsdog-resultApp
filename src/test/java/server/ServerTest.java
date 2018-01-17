package server;

import com.sun.net.httpserver.HttpServer;
import factories.HttpServerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import  static  org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest {

    private static Integer API_SERVER_PORT =
            Integer.getInteger(System.getProperty("API_SERVER_PORT","9000"),9000);

    @Mock
    private HttpServerFactory httpServerFactory;
    @Mock
    private HttpServer httpServerMock;
    @Mock
    private Route routeMock;

    @Test
    public void createServerCreateTheServer() throws IOException {
        // GIVEN
        Server server = new Server();
        // WHEN
        server.createServer(httpServerFactory, routeMock);
        // THEN
        verify(httpServerFactory).createHttpServer(API_SERVER_PORT);

    }

    @Test
    public void createServerInitializeRoute() throws IOException {
        // GIVEN
        Server server = new Server();
        when(httpServerFactory.createHttpServer(API_SERVER_PORT)).thenReturn(httpServerMock);
        // WHEN
        server.createServer(httpServerFactory,routeMock);
        // THEN
        verify(routeMock).createRoute(eq(httpServerMock),any(ResultByDateHandler.class));

    }
}