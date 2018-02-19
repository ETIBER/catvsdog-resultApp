package model.result;

import services.DatabaseConnectionService.DatabaseConnectionService;
import services.DatabaseConnectionService.DatabaseConnectionServiceImpl;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class ResultDAOImpl implements ResultDAO {

    private static final String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,\"createTime\",\"createdAt\"," +
            "\"updatedAt\")\n VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_BY_CLOSEST_DATE_SQL = "SELECT * FROM result WHERE \"createTime\" <= ?" +
            " ORDER BY \"createTime\" LIMIT 1;";
    private static final String SELECT_BY_DATE_SQL = "SELECT * FROM result WHERE \"createTime\" BETWEEN ? AND ? " +
            "ORDER BY \"createTime\" DESC LIMIT 1";

    // createdAt

    private Connection connection;

    public ResultDAOImpl() throws SQLException {
        this.connection = new DatabaseConnectionServiceImpl().getConnection();
    }

    public void create(Result result) throws SQLException {
        PreparedStatement insertPreparedStatement = this.connection.prepareStatement(INSERT_TABLE_SQL);
        insertPreparedStatement.setDouble(1, result.getCat());
        insertPreparedStatement.setDouble(2, result.getDog());
        insertPreparedStatement.setTimestamp(3, Timestamp.valueOf(result.getCreateTime()));
        insertPreparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        insertPreparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        insertPreparedStatement.execute();

    }

    public Result findClosestDate(LocalDateTime expectedDateTime) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_BY_CLOSEST_DATE_SQL);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(expectedDateTime));
        ResultSet resultSet = preparedStatement.executeQuery();
        return TransformResultSetToResult(resultSet);
    }

    @Override
    public Result findByDate(LocalDate expectedDate) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_BY_DATE_SQL);

        LocalDateTime startOfTheDay = expectedDate.atTime(LocalTime.MIN);
        LocalDateTime endOfTheDay = expectedDate.atTime(LocalTime.MAX);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(startOfTheDay));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(endOfTheDay));
        System.out.println("Execute query: "+preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return TransformResultSetToResult(resultSet);

    }

    private Result TransformResultSetToResult(ResultSet resultSet) throws SQLException {
        Result result = null;
        if (resultSet.next()) {
            double cat = resultSet.getDouble("cat");
            double dog = resultSet.getDouble("dog");
            LocalDateTime createTime = resultSet.getTimestamp("createTime").toLocalDateTime();
            result = new Result(cat, dog, createTime);
        }
        return result;
    }
}
