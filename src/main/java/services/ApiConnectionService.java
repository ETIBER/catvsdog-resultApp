package services;

import model.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiConnectionService {
    private  URL url = null;
    private HttpURLConnection httpURLConnection = null;

    public ApiConnectionService() {
        try {
            this.url = new URL("http://localhost:4000/api/v1/results");
            this.httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Result getResult() {
        StringBuilder result = new StringBuilder();

        try {
            this.httpURLConnection.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Result(result.toString());
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }
}
