package services;

import model.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnectionService {
    private static final String PG_HOST = System.getProperty("PG_HOST","localhost/result");
    private static final String PG_USER = System.getProperty("PG_USER","admin");
    private static final String PG_PASSWORD = System.getProperty("PG_PASSWORD","admin");

    private final String url;
    private final Properties properties;
    private Connection connection;

    protected DataBaseConnectionService(Connection connection){
        this.connection = connection;
        this.properties = null;
        this.url = null;
    }

    public DataBaseConnectionService() throws SQLException {
        this.url = String.format("jdbc:postgresql://%s", PG_HOST);
        this.properties = new Properties();
        properties.setProperty("user",PG_USER);
        properties.setProperty("password",PG_PASSWORD);
        this.connection = DriverManager.getConnection(this.url,this.properties);
    }

    //GETTER


    public Connection getConnection() {
        return connection;
    }
}
