package services.ApiConnectionService;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ApiConnectionResultServiceImpl implements ApiConnectionResultService {

    private static final String API_RESULT_HOST = System.getProperty("API_RESULT_HOST", "localhost");
    private static final Integer API_RESULT_PORT =
            Integer.getInteger(System.getProperty("API_RESULT_PORT", "4000"), 4000);
    private static final String PROTOCOL = "http";
    private static final String RESULT_FILE = "/api/v1/percentage-votes";

    private URLConnectionFactory urlConnectionFactory;
    private IOUtilsTools ioUtilsTools;

    public ApiConnectionResultServiceImpl() {
        this.urlConnectionFactory = new URLConnectionFactory();
        this.ioUtilsTools = new IOUtilsTools();
    }


    public Result getResult() throws IOException {
        HttpURLConnection httpURLConnection =
                (HttpURLConnection) urlConnectionFactory.getURLConnection(PROTOCOL, API_RESULT_HOST, API_RESULT_PORT, RESULT_FILE);
        httpURLConnection.setRequestMethod("GET");
        InputStream inputStream = httpURLConnection.getInputStream();
        Result result = new Result(ioUtilsTools.inputStreamToString(inputStream));
        inputStream.close();
        return result;
    }
}
