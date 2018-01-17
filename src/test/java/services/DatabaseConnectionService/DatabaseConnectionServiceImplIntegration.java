package services.DatabaseConnectionService;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DatabaseConnectionServiceImplIntegration {

    @Test
    public void testTheConnection() {
        // GIVEN
        // WHEN
        DatabaseConnectionServiceImpl databaseConnectionServiceImpl = null;
        try {
            databaseConnectionServiceImpl = new DatabaseConnectionServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = databaseConnectionServiceImpl.getConnection();
        // THEN
        Assert.assertTrue(connection != null);
    }
}