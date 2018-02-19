package services.DatabaseConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {
    private static final String PG_HOST = System.getenv("PG_HOST") != null
            ? System.getenv("PG_HOST"):"localhost/result";
    private static final String PG_USER = System.getenv("PG_USER") != null
            ?System.getenv("PG_USER") : "admin";
    private static final String PG_PASSWORD = System.getenv("PG_PASSWORD") != null
            ?System.getenv("PG_PASSWORD"):"admin";

    private Connection connection;


    public DatabaseConnectionServiceImpl() throws SQLException {
        String url = String.format("jdbc:postgresql://%s", PG_HOST);
        System.out.println("Try to connect to "+url+ " with properties :"+PG_USER+" "+PG_PASSWORD);
        Properties properties = new Properties();
        properties.setProperty("user", PG_USER);
        properties.setProperty("password", PG_PASSWORD);
        this.connection = DriverManager.getConnection(url, properties);
    }


    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
