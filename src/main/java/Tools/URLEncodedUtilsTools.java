package Tools;


import com.sun.net.httpserver.HttpExchange;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.util.List;


public class URLEncodedUtilsTools{

    public List<NameValuePair> getParameterFromHttpExchange(HttpExchange httpExchange) {
        return URLEncodedUtils.parse(httpExchange.getRequestURI(), "UTF-8");
        }
}
