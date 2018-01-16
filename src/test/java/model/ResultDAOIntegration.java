package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.DataBaseConnectionService;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResultDAOIntegration {


    private DataBaseConnectionService dataBaseConnectionService;
    private Connection connection;
    @Before
    public void setUp() throws SQLException {
        final String dropTableSQL = "DROP TABLE IF EXISTS result";

        dataBaseConnectionService = new DataBaseConnectionService();
        connection = dataBaseConnectionService.getConnection();
        connection.prepareStatement(dropTableSQL).execute();

    }

    @Test
    public void createAddALineInTheDatabaseCorrectlyWithInteger() throws SQLException, InsertionFailException {
        //GIVEN

        Result result = new Result(1,0);
        ResultDAO resultDAO = new ResultDAOImpl(connection);

        //WHEN
        resultDAO.create(result);

        //THEN
        PreparedStatement selectResult = connection.prepareStatement("SELECT * FROM result");
        selectResult.execute();
        ResultSet resultSet = selectResult.getResultSet();
        resultSet.next();
        int cat = resultSet.getInt(1);
        int dog = resultSet.getInt(2);
        Timestamp createTime = resultSet.getTimestamp(3);
        assertEquals(1,cat);
        assertEquals(0,dog);
        assertTrue(createTime != null);
    }

    @Test
    public void createAddALineInTheDatabaseCorrectlyWithFloat() throws SQLException, InsertionFailException {
        //GIVEN
        Result result = new Result(0.5,0.5);
        ResultDAO resultDAO = new ResultDAOImpl(connection);

        //WHEN
        resultDAO.create(result);

        //THEN
        PreparedStatement selectResult = connection.prepareStatement("SELECT * FROM result");
        selectResult.execute();
        ResultSet resultSet = selectResult.getResultSet();
        resultSet.next();
        Double cat = resultSet.getDouble(1);
        Double dog = resultSet.getDouble(2);
        Timestamp createTime = resultSet.getTimestamp(3);
        assertEquals(0.5,cat.doubleValue(),0);
        assertEquals(0.5,dog.doubleValue(),0);
        assertTrue(createTime != null);
    }

    @Test
    public void findGetALineOfTheExactDate() throws SQLException, InsertionFailException {
        //GIVEN
        LocalDateTime localDateTime = LocalDateTime.now();
        Result result = new Result(0.5,0.5, localDateTime);
        ResultDAO resultDAO = new ResultDAOImpl(connection);

        //WHEN
        resultDAO.create(result);

        //THEN
        PreparedStatement selectResult = connection.prepareStatement("SELECT * FROM result");
        selectResult.execute();
        ResultSet resultSet = selectResult.getResultSet();
        resultSet.next();
        Double cat = resultSet.getDouble(1);
        Double dog = resultSet.getDouble(2);
        Timestamp createTime = resultSet.getTimestamp(3);
        assertEquals(0.5,cat.doubleValue(),0);
        assertEquals(0.5,dog.doubleValue(),0);
        assertTrue(createTime != null);
    }
}
