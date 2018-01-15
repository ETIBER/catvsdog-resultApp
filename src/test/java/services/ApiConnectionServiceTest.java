package services;

        import model.Result;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;

        import java.io.ByteArrayInputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;

        import static org.mockito.Mockito.*;

public class ApiConnectionServiceTest {

    @Mock private HttpURLConnection httpURLConnection;

    InputStream fakeInputStream;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnAResultWhenCalled() throws IOException {
        //GIVEN
        ApiConnectionService apiConnectionService = new ApiConnectionService();

        apiConnectionService.setHttpURLConnection (httpURLConnection);
        String str = "{\"cat\":1 ,\"dog\":0}";
        fakeInputStream = new ByteArrayInputStream(str.getBytes());
        InputStream fakeInputStream = new ByteArrayInputStream(str.getBytes());
        //WHEN
        when(apiConnectionService.getHttpURLConnection().getInputStream()).thenReturn(fakeInputStream);
        Result result = apiConnectionService.getResult();
        //THEN
        verify(apiConnectionService.getHttpURLConnection(),times(1)).getInputStream();
        Assert.assertTrue(result != null);

    }

    @Test
    public void returnAResultWithGoodParameterWhenCalled() throws IOException {
        //GIVEN
        ApiConnectionService apiConnectionService = new ApiConnectionService();

        apiConnectionService.setHttpURLConnection (httpURLConnection);
        String str = "{\"cat\":1 ,\"dog\":0}";
        InputStream fakeInputStream = new ByteArrayInputStream(str.getBytes());
        //WHEN
        when(apiConnectionService.getHttpURLConnection().getInputStream()).thenReturn(fakeInputStream);
        Result result = apiConnectionService.getResult();
        //THEN
        verify(apiConnectionService.getHttpURLConnection(),times(1)).getInputStream();
        Assert.assertEquals(new Result(str),result);

    }
}