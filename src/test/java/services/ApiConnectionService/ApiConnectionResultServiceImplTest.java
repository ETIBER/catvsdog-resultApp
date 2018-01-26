package services.ApiConnectionService;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApiConnectionResultServiceImplTest {

    private static final String API_RESULT_HOST = System.getProperty("API_RESULT_HOST","localhost");
    private static final Integer API_RESULT_PORT =
            Integer.getInteger(System.getProperty("API_RESULT_PORT","4000"),4000);
    private static final String PROTOCOL = "http";
    private static final String RESULT_FILE = "/api/v1/percentage-votes";



    @Mock
    private URLConnectionFactory urlConnectionFactoryMock;
    @Mock
    private IOUtilsTools ioUtilsToolsMock;
    @InjectMocks
    private ApiConnectionResultServiceImpl apiConnectionService;


    @Mock
    private HttpURLConnection httpURLConnectionMock;
    @Mock
    private InputStream inputStreamMock;



    @Before
    public void setUp(){
    }

    @Test
    public void getResultReturnNotNullResult() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        Assert.assertNotNull(result);
    }

    @Test
    public void getResultGetURLConnection() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        verify(urlConnectionFactoryMock).getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE);
    }

    @Test
    public void getResultSetRequestMethod() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        verify(httpURLConnectionMock).setRequestMethod("GET");
    }

    @Test
    public void getResultGetAnInputStream() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        verify(httpURLConnectionMock).getInputStream();
    }

    @Test
    public void getResultConvertInputStreamInString() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        verify(ioUtilsToolsMock).inputStreamToString(inputStreamMock);
    }

    @Test
    public void getResultConvertStringToResult() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        Assert.assertEquals(expectedResult.getCat(),result.getCat());
        Assert.assertEquals(expectedResult.getDog(),result.getDog());
    }

    @Test
    public void getResultCloseInputStream() throws IOException {
        // GIVEN
        String jsonResult = "{\"cat\":1 ,\"dog\":0}";
        Result expectedResult = new Result(jsonResult);
        when(urlConnectionFactoryMock.getURLConnection(PROTOCOL,API_RESULT_HOST,API_RESULT_PORT,RESULT_FILE))
                .thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(ioUtilsToolsMock.inputStreamToString(inputStreamMock)).thenReturn(jsonResult);

        // WHEN
        Result result = apiConnectionService.getResult();
        // THEN
        verify(inputStreamMock).close();
    }

}