package factories;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionFactory {
    public URLConnection getURLConnection(String protocol, String host, Integer port, String file) throws IOException {
        return new URL(protocol, host, port, file).openConnection();
    }
}
