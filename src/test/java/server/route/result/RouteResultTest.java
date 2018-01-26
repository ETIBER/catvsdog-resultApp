package server.route.result;

import com.sun.net.httpserver.HttpServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import server.route.result.ResultByDateHandler;
import server.route.result.RouteResult;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteResultTest {

    private final String BASE = "/api";
    private final String VERSION = "/v1";
    private final String RESULT_SERVICE = "/vote-percentages";


@Mock
private HttpServer httpServerMock;
    @Mock
    private ResultByDateHandler resultByDateHandlerMock;


    @Test
    public void createARouteForGetResultByDate() {
        // GIVEN
        RouteResult routeResult = new RouteResult();
        // WHEN
        routeResult.createRouteResult(httpServerMock, resultByDateHandlerMock);
        // THEN
        verify(httpServerMock).createContext(BASE+VERSION+RESULT_SERVICE, resultByDateHandlerMock);
    }

}