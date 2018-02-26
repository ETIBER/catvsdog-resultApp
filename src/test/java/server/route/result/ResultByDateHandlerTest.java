package server.route.result;

import com.sun.net.httpserver.Headers;
import model.result.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sun.net.httpserver.HttpExchange;
import server.route.result.ResultByDateHandler;
import services.ResultService.ResultService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;

import  static  org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultByDateHandlerTest {



    @Mock
    HttpExchange httpExchangeMock;
    @Mock
    ResultService resultServiceMock;

    ResultByDateHandler resultByDateHandler;

    @Mock
    OutputStream outputStreamMock;
    @Mock
    Result resultMock;
    @Mock
    Headers headersMock;


    @Before
    public void SetUp() throws SQLException {
        resultByDateHandler = new ResultByDateHandler(resultServiceMock);

    }


    @Test
    public void handleGetTheResponseBody() throws IOException, URISyntaxException, SQLException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedJsonString = "{\"cat:\":0.0 ,\"dog\":1.0}";
        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(resultMock);
        when(resultMock.toJSON()).thenReturn(expectedJsonString);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(httpExchangeMock).getResponseBody();
    }

    @Test
    public void handleGetTheDateParameter() throws IOException, URISyntaxException, SQLException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedJsonString = "{\"cat:\":0.0 ,\"dog\":1.0}";
        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(resultMock);
        when(resultMock.toJSON()).thenReturn(expectedJsonString);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        //THEN
        verify(httpExchangeMock).getRequestURI();
    }
    @Test
    public void handleCallResultServiceWithGoodParameter() throws IOException, SQLException, URISyntaxException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedJsonString = "{\"cat:\":0.0 ,\"dog\":1.0}";
        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(resultMock);
        when(resultMock.toJSON()).thenReturn(expectedJsonString);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(resultServiceMock).getResultByDate(expectedLocalDate);
    }
    @Test
    public void handleReturnResultInStreamWhenValidResult() throws IOException, SQLException, URISyntaxException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedJsonString = "{\"cat:\":0.0 ,\"dog\":1.0}";
        Integer expectedRCode = 200;
        Long expectedLength = (long) expectedJsonString.length();

        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(resultMock);
        when(resultMock.toJSON()).thenReturn(expectedJsonString);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(httpExchangeMock).sendResponseHeaders(eq(expectedRCode),eq(expectedLength));
        verify(outputStreamMock).write(expectedJsonString.getBytes());
    }

    @Test
    public void handleReturnSpecificErrorInStreamWhenUnValidTimeFormat() throws IOException, SQLException, URISyntaxException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=201");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String errorString = "Error in date format";
        Integer expectedRCode = 400;
        Long expectedLength = (long) errorString.length();


        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(httpExchangeMock).sendResponseHeaders(eq(expectedRCode),eq(expectedLength));
        verify(outputStreamMock).write(errorString.getBytes());
    }

    @Test
    public void handleCloseTheOutputStream() throws URISyntaxException, SQLException, IOException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedJsonString = "{\"cat:\":0.0 ,\"dog\":1.0}";
        Integer expectedRCode = 200;
        Long expectedLength = (long) expectedJsonString.length();

        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(resultMock);
        when(resultMock.toJSON()).thenReturn(expectedJsonString);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(outputStreamMock).close();

    }

    @Test
    public void handleReturnNullIfInResultIsNull() throws IOException, SQLException, URISyntaxException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedOutputStreamString = "{}";
        Integer expectedRCode = 404;
        Long expectedLength = (long) expectedOutputStreamString.length();

        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(null);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(outputStreamMock).write(eq(expectedOutputStreamString.getBytes()));
    }

    @Test
    public void handleGenerateCrossOriginHeader() throws IOException, SQLException, URISyntaxException {
        // GIVEN
        URI uri = new URI("http://www.example.com/something.html?date=2018-01-01");
        LocalDate expectedLocalDate = LocalDate.of(2018,1,1);
        String expectedOutputStreamString = "{}";
        Integer expectedRCode = 404;
        Long expectedLength = (long) expectedOutputStreamString.length();

        when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headersMock);
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStreamMock);
        when(resultServiceMock.getResultByDate(any())).thenReturn(null);
        // WHEN
        resultByDateHandler.handle(httpExchangeMock);
        // THEN
        verify(headersMock).add("Access-Control-Allow-Origin","*");


    }





}