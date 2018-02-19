package services.ApiConnectionService;


import model.result.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ApiConnectionResultServiceImplIntegration {
    @Test
    public void getResultReturnAResult() throws IOException {
        //GIVEN
        ApiConnectionResultService apiConnectionResultService = new ApiConnectionResultServiceImpl();
        //WHEN
        Result result = apiConnectionResultService.getResult();
        //THEN
        Assert.assertNotNull(result);
    }

}
