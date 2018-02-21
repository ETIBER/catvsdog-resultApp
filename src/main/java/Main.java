import scheduler.ImportResultSchedulerImpl;
import server.Server;

import java.io.IOException;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        new ImportResultSchedulerImpl().execute();
        try {
            new Server().createServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
