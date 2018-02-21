package services.DatabaseConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionDriverService {
    public Connection getConnection(String url,Properties properties) throws SQLException {
        return DriverManager.getConnection(url,properties);
    }
}
