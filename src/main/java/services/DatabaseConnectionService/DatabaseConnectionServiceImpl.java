package services.DatabaseConnectionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {
    private static final Logger logger = LogManager.getLogger("DatabaseConnectionServiceImpl");

    private static final String PG_HOST = System.getenv("POSTGRES_HOST") != null
            ? System.getenv("POSTGRES_HOST") : "localhost/result";
    private static final String PG_USER = System.getenv("POSTGRES_USER") != null
            ? System.getenv("POSTGRES_USER") : "postgres";
    private static final String PG_PASSWORD = System.getenv("POSTGRES_PASSWORD") != null
            ? System.getenv("POSTGRES_PASSWORD") : "postgres";

    private DatabaseConnectionDriverService databaseConnectionDriverService;
    private Properties properties;


    public DatabaseConnectionServiceImpl() {
        this.databaseConnectionDriverService = new DatabaseConnectionDriverService();
        this.properties = new Properties();
    }


    @Override
    public Connection getConnection() {

        String url = String.format("jdbc:postgresql://%s", PG_HOST);
        properties.setProperty("user", PG_USER);
        properties.setProperty("password", PG_PASSWORD);

        return this.connect(url,properties);
    }

    private Connection connect(String url, Properties properties) {

        Connection connection = null;
        try {
            logger.info("Try to connect to " + url + " with properties :" + PG_USER + " " + PG_PASSWORD);
            connection = databaseConnectionDriverService.getConnection(url, this.properties);
            logger.info("Database connection successful");
        } catch (SQLException e) {
            logger.error("error during database connection, retry");
            try {
                synchronized (this){
                    wait(1000);
                    return this.connect(url,properties);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }
        return connection;
    }
}

