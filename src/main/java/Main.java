import factories.HttpServerFactory;
import scheduler.ImportResultSchedulerImpl;
import server.Server;
import server.route.result.RouteResult;
import services.ApiConnectionService.ApiConnectionResultServiceImpl;
import services.ResultService.ResultServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) throws SQLException {
        new ImportResultSchedulerImpl(new ApiConnectionResultServiceImpl(),new ResultServiceImpl(),
                Executors.newScheduledThreadPool(1)).execute();
        try {
            new Server(new HttpServerFactory(),new RouteResult()).createServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
