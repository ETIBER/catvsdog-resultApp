package services.ApiConnectionService;


import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ApiConnectionServiceImplIntegration {
    private static final String API_RESULT_HOST = System.getProperty("API_RESULT_HOST","localhost");
    private static final Integer API_RESULT_PORT =
            Integer.getInteger(System.getProperty("API_RESULT_PORT","4000"),4000);
    private static final String PROTOCOL = "http";
    private static final String RESULT_FILE = "api/v1/result";

    @Test
    public void getResultReturnAResult() throws IOException {
        //GIVEN
        ApiConnectionService apiConnectionService = new ApiConnectionServiceImpl();
        //WHEN
        Result result = apiConnectionService.getResult(new URLConnectionFactory(),new IOUtilsTools());
        //THEN
        Assert.assertNotNull(result);
    }

}
