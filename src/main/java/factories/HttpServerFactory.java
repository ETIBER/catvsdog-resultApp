package factories;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerFactory {
    public HttpServer createHttpServer (Integer port) throws IOException {
        return  HttpServer.create(new InetSocketAddress(port),0);
    }
}
