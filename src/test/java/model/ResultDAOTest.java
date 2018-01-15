package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import services.DataBaseConnectionService;

import java.lang.annotation.Inherited;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResultDAOTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createReturnResult() throws SQLException {
        // GIVEN
        Result result = new Result(1,0);
        String sqlCreate = "INSERT INTO result (cat,dog,createTime)\n" +
                " VALUES (?, ?, ?);";
        ResultDAO resultDAO = new ResultDAO(connection);
        // WHEN
        doNothing().when(preparedStatement).setDouble(anyInt(),anyDouble());
        doNothing().when(preparedStatement).setTimestamp(anyInt(),any());
        when(resultDAO.getConnection().prepareStatement(sqlCreate)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        Boolean success = resultDAO.create(result);

        //THEN
        Assert.assertTrue(success);
    }
    @Test
    public void createPrepareAStatementAndCreateResult() throws SQLException {
        // GIVEN
        Result result = new Result(1,0);
        String sqlCreate = "INSERT INTO result (cat,dog,createTime)\n" +
              " VALUES (?, ?, ?);";
        ResultDAO resultDAO = new ResultDAO(connection);
        // WHEN
        doNothing().when(preparedStatement).setDouble(anyInt(),anyDouble());
        doNothing().when(preparedStatement).setTimestamp(anyInt(),any());
        when(resultDAO.getConnection().prepareStatement(sqlCreate)).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        Boolean success = resultDAO.create(result);

        //THEN
        Assert.assertTrue(success);
        Mockito.verify(resultDAO.getConnection(),times(1)).prepareStatement(sqlCreate);
    }

}