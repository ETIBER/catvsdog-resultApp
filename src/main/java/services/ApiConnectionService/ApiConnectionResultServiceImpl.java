package services.ApiConnectionService;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ApiConnectionResultServiceImpl implements ApiConnectionResultService {

    private static final Logger logger = LogManager.getLogger(" ApiConnectionResultServiceImpl");

    private static final String API_RESULT_HOST = (System.getenv("API_RESULT_HOST") != null)
            ? System.getenv("API_RESULT_HOST") : "localhost";
    private static final Integer API_RESULT_PORT =
            Integer.getInteger(System.getenv("API_RESULT_PORT") != null
                    ? System.getenv("API_RESULT_PORT") : "5001",5001);
    private static final String PROTOCOL = "http";
    private static final String RESULT_FILE = "/api/v1/percentage-votes";

    private URLConnectionFactory urlConnectionFactory;
    private IOUtilsTools ioUtilsTools;

    public ApiConnectionResultServiceImpl() {
        this.urlConnectionFactory = new URLConnectionFactory();
        this.ioUtilsTools = new IOUtilsTools();
    }


    public Result getResult() {
        HttpURLConnection httpURLConnection = null;
        Result result = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) urlConnectionFactory.getURLConnection(PROTOCOL, API_RESULT_HOST, API_RESULT_PORT, RESULT_FILE);
            httpURLConnection.setRequestMethod("GET");
            logger.info("Try to connect to: "+ httpURLConnection.getURL());
            logger.debug("Header:"+httpURLConnection.getHeaderFields().toString());
            inputStream = httpURLConnection.getInputStream();
            result = new Result(ioUtilsTools.inputStreamToString(inputStream));
            inputStream.close();
            logger.info("Successfully connected to: "+ httpURLConnection.toString());
        } catch (IOException e) {
            assert httpURLConnection != null;
            logger.error("Error when connect to:"+ httpURLConnection.toString(),e);
        }

        return result;
    }
}
