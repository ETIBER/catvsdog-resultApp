package services;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class DataBaseConnectionServiceTest {
    @Test
    public void testTheConnection() {
        // GIVEN
        // WHEN
        DataBaseConnectionService dataBaseConnectionService = null;
        try {
            dataBaseConnectionService = new DataBaseConnectionService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = dataBaseConnectionService.getConnection();
        // THEN
        Assert.assertTrue(connection != null);
    }




}