package services.ApiConnectionService;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;

import java.io.IOException;

public interface ApiConnectionService {
    Result getResult(URLConnectionFactory urlFactoryMock, IOUtilsTools ioUtilsToolsMock) throws IOException;
}
