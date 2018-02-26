package model.result;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import services.DatabaseConnectionService.DatabaseConnectionServiceImpl;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResultDAOIntegration {

    @Mock
    private Connection connection;
    @Mock
    private DatabaseConnectionServiceImpl databaseConnectionServiceImpl;

    @Before
    public void setUp() throws SQLException {
        final String dropTableSQL = "DROP TABLE IF EXISTS result";
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS result(cat FLOAT, dog FLOAT," +
                " createTime TIMESTAMP PRIMARY KEY);";

        databaseConnectionServiceImpl = new DatabaseConnectionServiceImpl();
        connection = databaseConnectionServiceImpl.getConnection();
        connection.prepareStatement(dropTableSQL).execute();
        connection.prepareStatement(createTableSQL).execute();

    }

    @Test
    public void createAddALineInTheDatabaseCorrectlyWithInteger() throws SQLException {
        //GIVEN
        Result result = new Result(1D,0D,LocalDateTime.now());
        ResultDAO resultDAO = new ResultDAOImpl();

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
    public void createAddALineInTheDatabaseCorrectlyWithFloat() throws SQLException {
        //GIVEN
        Result expectedResult = new Result(0.4,0.6);
        ResultDAO resultDAO = new ResultDAOImpl();

        //WHEN
        resultDAO.create(expectedResult);

        //THEN
        PreparedStatement selectResult = connection.prepareStatement("SELECT * FROM result");
        selectResult.execute();
        ResultSet resultSet = selectResult.getResultSet();
        resultSet.next();
        Double cat = resultSet.getDouble("cat");
        Double dog = resultSet.getDouble("dog");
        LocalDateTime createTime = resultSet.getTimestamp("createTime").toLocalDateTime();
        assertEquals(expectedResult.getCat(), cat,0);
        assertEquals(expectedResult.getDog(), dog,0);
        assertEquals(expectedResult.getCreateTime(),createTime);
    }

    @Test
    public void findClosestDateGetALineOfTheExactDate() throws SQLException {
        //GIVEN
        Result expectedResult = new Result(0.6,0.4, LocalDateTime.now());
        ResultDAO resultDAO = new ResultDAOImpl();
        final String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,createTime)\n VALUES (?, ?, ?);";
        PreparedStatement insertPreparedStatement = this.connection.prepareStatement(INSERT_TABLE_SQL);
        insertPreparedStatement.setDouble(1,expectedResult.getCat());
        insertPreparedStatement.setDouble(2,expectedResult.getDog());
        insertPreparedStatement.setTimestamp(3, Timestamp.valueOf(expectedResult.getCreateTime()));
        insertPreparedStatement.execute();

        //WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());

        //THEN
        assertEquals(expectedResult.getCat(), result.getCat(),0);
        assertEquals(expectedResult.getDog(), result.getDog(),0);
        assertEquals(expectedResult.getCreateTime(),result.getCreateTime());
    }

    @Test
    public void CreateAndFindClosestDateWorkTogether() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.6,0.4, LocalDateTime.now());
        ResultDAO resultDAO = new ResultDAOImpl();

        // WHEN
        resultDAO.create(expectedResult);
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());

        //THEN
        assertEquals(expectedResult.getCat(), result.getCat(),0);
        assertEquals(expectedResult.getDog(), result.getDog(),0);
        assertEquals(expectedResult.getCreateTime(),result.getCreateTime());
    }

    @Test
    public void CreateAndFindByDateWorkTogether() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.6,0.4, LocalDateTime.now());
        ResultDAO resultDAO = new ResultDAOImpl();

        // WHEN
        resultDAO.create(expectedResult);
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());

        //THEN
        assertEquals(expectedResult.getCat(), result.getCat(),0);
        assertEquals(expectedResult.getDog(), result.getDog(),0);
        assertEquals(expectedResult.getCreateTime(),result.getCreateTime());
    }
}
