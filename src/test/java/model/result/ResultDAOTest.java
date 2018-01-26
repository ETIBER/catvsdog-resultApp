package model.result;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultDAOTest {

    private final static String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,createTime)\n VALUES (?, ?, ?);";
    private static final String SELECT_BY_CLOSEST_DATE_SQL = "SELECT * FROM result WHERE createTime <= ?" +
            " ORDER BY createTime LIMIT 1;";
    private static final String SELECT_BY_DATE_SQL = "SELECT * FROM result WHERE createTime BETWEEN ? AND ? " +
            "ORDER BY createTime DESC LIMIT 1";

    @Mock
    private Connection connectionMock;
    @InjectMocks
    private ResultDAOImpl resultDAO;

    @Mock
    private PreparedStatement preparedStatementInsertTable;
    @Mock
    private PreparedStatement preparedStatementSelectTable;
    @Mock
    private ResultSet resultSetMock;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void SetUp() throws SQLException {
    }

    @Test
    public void createPrepareAInsertStatement() throws SQLException{
        // GIVEN
        Result result = new Result(1,0);
        when(connectionMock.prepareStatement(INSERT_TABLE_SQL)).thenReturn(preparedStatementInsertTable);
        doNothing().when(preparedStatementInsertTable).setDouble(anyInt(),anyDouble());
        doNothing().when(preparedStatementInsertTable).setTimestamp(anyInt(),any());
        when(preparedStatementInsertTable.execute()).thenReturn(true);
        // WHEN
        resultDAO.create(result);
        // THEN
        verify(connectionMock).prepareStatement(INSERT_TABLE_SQL);
    }

    @Test
    public void createSetTheValueOfThePrepareStatement() throws SQLException{
        // GIVEN
        Result result = new Result(1,0);
        when(connectionMock.prepareStatement(INSERT_TABLE_SQL)).thenReturn(preparedStatementInsertTable);
        doNothing().when(preparedStatementInsertTable).setDouble(anyInt(),anyDouble());
        doNothing().when(preparedStatementInsertTable).setTimestamp(anyInt(),any());
        when(preparedStatementInsertTable.execute()).thenReturn(true);
        // WHEN
        resultDAO.create(result);
        // THEN
        verify(preparedStatementInsertTable).setDouble(eq(1),eq(1D));
        verify(preparedStatementInsertTable).setDouble(eq(2),eq(0D));
        verify(preparedStatementInsertTable).setTimestamp(eq(3),eq(Timestamp.valueOf(result.getCreateTime())));
    }

    @Test
    public void createExecuteThePrepareStatement() throws SQLException {
        // GIVEN
        Result result = new Result(1,0);
        when(connectionMock.prepareStatement(INSERT_TABLE_SQL)).thenReturn(preparedStatementInsertTable);
        doNothing().when(preparedStatementInsertTable).setDouble(anyInt(),anyDouble());
        doNothing().when(preparedStatementInsertTable).setTimestamp(anyInt(),any());
        when(preparedStatementInsertTable.execute()).thenReturn(true);
        // WHEN
        resultDAO.create(result);
        // THEN
        verify(preparedStatementInsertTable).execute();
    }


    @Test
    public void findClosestDatePrepareASelectStatement() throws SQLException {
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        verify(connectionMock).prepareStatement(SELECT_BY_CLOSEST_DATE_SQL);
    }

    @Test
    public void findClosestDateSetTheCreateDateValueInsideTheStatement() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        verify(preparedStatementSelectTable)
                .setTimestamp(eq(1),eq(Timestamp.valueOf(expectedResult.getCreateTime())));
    }

    @Test
    public void findClosestDateExecuteTheStatement() throws SQLException {
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        verify(preparedStatementSelectTable).executeQuery();
    }

    @Test
    public void findClosestDateReturnResultWhenNextReturnTrue() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        Assert.assertNotNull(result);

    }

    @Test
    public void findClosestDateReturnNullWhenNextReturnFalse() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        Assert.assertNull(result);

    }

    @Test
    public void findClosestDateRetrieveInformationFromResultSetIfExist()  throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        verify(resultSetMock).getDouble("cat");
        verify(resultSetMock).getDouble("dog");
        verify(resultSetMock).getTimestamp("createTime");

    }

    @Test
    public void findClosestDateRetrieveAResultWithParameterRetrieveFromRequest()  throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findClosestDate(expectedResult.getCreateTime());
        // THEN
        Assert.assertEquals(expectedResult,result);

    }

    @Test
    public void findByDatePrepareASelectStatement() throws SQLException {
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));

        // WHEN
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());
        // THEN
        verify(connectionMock).prepareStatement(SELECT_BY_DATE_SQL);
    }

    @Test
    public void findByDateSetTheBeginningAndTheEndOfTheDay() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        LocalDateTime startOfTheDay = expectedResult.getCreateTime().toLocalDate().atStartOfDay();
        LocalDateTime endOfTheDay = expectedResult.getCreateTime().toLocalDate().atTime(LocalTime.MAX);
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());
        // THEN
        verify(preparedStatementSelectTable).setTimestamp(eq(1),eq(Timestamp.valueOf(startOfTheDay)));
        verify(preparedStatementSelectTable).setTimestamp(eq(2),eq(Timestamp.valueOf(endOfTheDay)));
    }

    @Test
    public void findByDateExecuteTheStatement() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());
        // THEN
        verify(preparedStatementSelectTable).executeQuery();
    }

    @Test
    public void findByDateReturnResultWhenNextReturnTrue() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());
        // THEN
        Assert.assertNotNull(result);

    }

    @Test
    public void findByDateReturnNullWhenNextReturnTrue() throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);
        // WHEN
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());
        // THEN
        Assert.assertNull(result);

    }

    @Test
    public void findByDateRetrieveAResultWithParameterRetrieveFromRequest()  throws SQLException {
        // GIVEN
        Result expectedResult = new Result(0.75,0.25);
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getDouble("cat")).thenReturn(expectedResult.getCat());
        when(resultSetMock.getDouble("dog")).thenReturn(expectedResult.getDog());
        when(resultSetMock.getTimestamp("createTime"))
                .thenReturn(Timestamp.valueOf(expectedResult.getCreateTime()));
        // WHEN
        Result result = resultDAO.findByDate(expectedResult.getCreateTime().toLocalDate());
        // THEN
        Assert.assertEquals(expectedResult,result);

    }
}