package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultDAOTest {

    private final static String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,createTime)\n VALUES (?, ?, ?);";
    private static final String SELECT_BY_DATE_SQL = "SELECT * FROM result WHERE createTime < ?" +
            " ORDER BY createDate LIMIT 1;";

    private ResultDAO resultDAO;

    @Mock
    private Connection connectionMock;
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
        resultDAO = new ResultDAOImpl(connectionMock);
    }

    @Test
    public void createPrepareAInsertStatement() throws SQLException, InsertionFailException {
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
    public void createSetTheValueOfThePrepareStatement() throws SQLException, InsertionFailException {
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
    public void createExecuteThePrepareStatement() throws SQLException, InsertionFailException {
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
    public void createThrowAnErrorIfExecuteReturnFalse() throws SQLException, InsertionFailException {
        // GIVEN
        Result result = new Result(1,0);
        when(connectionMock.prepareStatement(INSERT_TABLE_SQL)).thenReturn(preparedStatementInsertTable);
        doNothing().when(preparedStatementInsertTable).setDouble(anyInt(),anyDouble());
        doNothing().when(preparedStatementInsertTable).setTimestamp(anyInt(),any());
        when(preparedStatementInsertTable.execute()).thenReturn(false);
        // WHEN
        exception.expect(InsertionFailException.class);
        resultDAO.create(result);
        // THEN

    }

    @Test
    public void findPrepareASelectStatement() throws SQLException {
        // GIVEN
        LocalDateTime localDateTime = LocalDateTime.now();
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        // WHEN
        resultDAO.findByDate(localDateTime);
        // THEN
        verify(connectionMock).prepareStatement(SELECT_BY_DATE_SQL);
    }

    @Test
    public void findSetTheCreateDateValueInsideTheStatement() throws SQLException {
        // GIVEN
        LocalDateTime localDateTime = LocalDateTime.now();
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        // WHEN
        resultDAO.findByDate(localDateTime);
        // THEN
        verify(preparedStatementSelectTable).setTimestamp(eq(1),eq(Timestamp.valueOf(localDateTime)));
    }

    @Test
    public void findExecuteTheStatement() throws SQLException {
        // GIVEN
        LocalDateTime localDateTime = LocalDateTime.now();
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        // WHEN
        resultDAO.findByDate(localDateTime);
        // THEN
        verify(preparedStatementSelectTable).executeQuery();
    }

    @Test
    public void findReturnResultWhenNextReturnTrue() throws SQLException {
        // GIVEN
        LocalDateTime localDateTime = LocalDateTime.now();
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        // WHEN
        Result result = resultDAO.findByDate(localDateTime);
        // THEN
        Assert.assertNotNull(result);

    }

    @Test
    public void findReturnNullWhenNextReturnFalse() throws SQLException {
        // GIVEN
        LocalDateTime localDateTime = LocalDateTime.now();
        when(connectionMock.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementSelectTable);
        when(preparedStatementSelectTable.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);
        // WHEN
        Result result = resultDAO.findByDate(localDateTime);
        // THEN
        Assert.assertNull(result);

    }


//    @Test
//    public void createSetTheValueOfThePrepareStatement() {
//        // GIVEN
//        ResultDAO resultDAO = new ResultDAOImpl(connectionMock);
//        Result result = new Result(1,0);
//        final String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,createTime)\n VALUES (?, ?, ?);";
//        // WHEN
//        resultDAO.create(result);
//        // THEN
//        verify(connectionMock).
//    }


//    @Mock
//    private Connection connection;
//    @Mock
//    private PreparedStatement preparedStatementCreateTable;
//    @Mock
//    private PreparedStatement preparedStatementInsertTable;
//    @Mock
//    private PreparedStatement preparedStatementInsertTable;
//    @Mock
//    private Result resultMock;
//    @Mock
//    private ResultSet resultSetMock;
//
//    private ResultDAO resultDAOMock;
//
//    final String createTableSQL = "CREATE TABLE IF NOT EXISTS result(cat FLOAT, dog FLOAT," +
//            " createTime TIMESTAMP PRIMARY KEY);";
//
//
//    @Before
//    public void setUp() throws SQLException {
//        MockitoAnnotations.initMocks(this);
//        when(connection.prepareStatement(createTableSQL)).thenReturn(preparedStatementCreateTable);
//        when(preparedStatementCreateTable.execute()).thenReturn(true);
//        this.resultDAOMock = new ResultDAOImpl(connection);
//    }
//
//    @Test
//    public void createPrepareAStatementExecuteItAndCreateResult() throws SQLException {
//        // GIVEN
//        Result result = new Result(1,0);
//        final String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,createTime)\n VALUES (?, ?, ?);";
//        // WHEN
//        when(connection.prepareStatement(INSERT_TABLE_SQL)).thenReturn(preparedStatementInsertTable);
//        doNothing().when(preparedStatementCreateTable).setDouble(anyInt(),anyDouble());
//        doNothing().when(preparedStatementCreateTable).setTimestamp(anyInt(),any());
//        when(preparedStatementCreateTable.execute()).thenReturn(true);
//        resultDAOMock.create(result);
//
//        //THEN
//        verify(connection,times(1)).prepareStatement(createTableSQL);
//        verify(connection,times(1)).prepareStatement(INSERT_TABLE_SQL);
//        verify(preparedStatementInsertTable,times(2)).setDouble(anyInt(),anyDouble());
//        verify(preparedStatementInsertTable,times(1))
//                .setTimestamp(eq(3),any(Timestamp.class));
//        verify(preparedStatementInsertTable,times(1)).execute();
//    }
//
//    @Test
//    public void findCallCreateStatementAndGetResult() throws SQLException {
//        // GIVEN
//        LocalDateTime localDateTime = LocalDateTime.now();
//        Timestamp timestamp = Timestamp.valueOf(localDateTime);
//        Double cat = 1d;
//        Double dog = 0d;
//
//        // WHEN
//        when(connection.prepareStatement(SELECT_BY_DATE_SQL)).thenReturn(preparedStatementInsertTable);
//        when(preparedStatementInsertTable.execute()).thenReturn(true);
//        when(preparedStatementInsertTable.getResultSet()).thenReturn(resultSetMock);
//        when(resultSetMock.next()).thenReturn(true);
//        when(resultSetMock.getDouble(1)).thenReturn(cat);
//        when(resultSetMock.getDouble(2)).thenReturn(dog);
//        when(resultSetMock.getTimestamp(3)).thenReturn(timestamp);
//        Result result = resultDAOMock.findByDate(localDateTime);
//
//        // THEN
//        Assert.assertEquals(new Result(cat,dog),result);
//        verify(connection,times(2)).prepareStatement(anyString());
//        verify(preparedStatementInsertTable,times(1)).execute();
//        verify(preparedStatementInsertTable,times(1)).getResultSet();
//        verify(resultSetMock,times(2)).next();
//        verify(resultSetMock,times(2)).getDouble(anyInt());
//        verify(resultSetMock,times(1)).getTimestamp(anyInt());
//
//
//    }

}