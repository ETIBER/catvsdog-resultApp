package server.route.result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.result.Result;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import server.UnValidParameterException;
import services.ResultService.ResultService;
import services.ResultService.ResultServiceImpl;

import java.net.URI;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResultByDateHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger(ResultByDateHandler.class);
    private ResultService resultService;

    public ResultByDateHandler() throws SQLException {
        resultService = new ResultServiceImpl();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String client = exchange.toString();
        logger.info("client: "+client+ "access to: "+uri.toString());
        Result result;
        String resultAnswerString = "";
        Integer rCode = 400;
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

        OutputStream outputStream = exchange.getResponseBody();
        try {
            result = resultService.getResultByDate(getLocalDateFromURI(uri));
            if (result != null){
                resultAnswerString = result.toJSON();
                rCode = 200;
            } else {
                resultAnswerString = "{}";
                rCode = 404;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnValidParameterException e) {
            resultAnswerString = "Error in date format";
        } finally {
            exchange.sendResponseHeaders(rCode,resultAnswerString.length());
            outputStream.write(resultAnswerString.getBytes());
            outputStream.close();
        }
    }

    private LocalDate getLocalDateFromURI(URI uri) throws UnValidParameterException {
        List<NameValuePair> parameters = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8.name());
        LocalDate localDate = null;
        for (NameValuePair param : parameters) {
            if (param.getName().equals("date")){
                try {
                    localDate  = LocalDate.parse(param.getValue());
                } catch (DateTimeParseException e) {
                    throw new UnValidParameterException();
                }
            }
        }

        if (localDate == null) throw new UnValidParameterException();


        return localDate;
    }

}
