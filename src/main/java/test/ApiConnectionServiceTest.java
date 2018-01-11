package test;

import model.Result;
import org.junit.Assert;
import org.junit.Test;
import services.ApiConnectionService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.mockito.Mockito.*;

public class ApiConnectionServiceTest {
    @Test
    public void returnAResultWhenCalled() throws IOException {
        //GIVEN
        ApiConnectionService apiConnectionService = new ApiConnectionService();

        apiConnectionService.setHttpURLConnection (mock(HttpURLConnection.class));
        String str = "{\"cat\":1 ,\"dog\":0}";
        InputStream fakeInputStream = new ByteArrayInputStream(str.getBytes());
        //WHEN
        when(apiConnectionService.getHttpURLConnection().getInputStream()).thenReturn(fakeInputStream);
        Result result = apiConnectionService.getResult();
        //THEN
        // verify(apiConnectionService.getHttpURLConnection().getInputStream(),times(1));
        Assert.assertTrue(result instanceof Result);

    }

    @Test
    public void returnAResultWithGoodParameterWhenCalled() throws IOException {
        //GIVEN
        ApiConnectionService apiConnectionService = new ApiConnectionService();

        apiConnectionService.setHttpURLConnection (mock(HttpURLConnection.class));
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