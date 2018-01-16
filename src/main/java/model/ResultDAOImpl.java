package model;
import java.sql.*;
import java.time.LocalDateTime;

public class ResultDAOImpl implements ResultDAO {
    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS result(cat FLOAT, dog FLOAT," +
            " createTime TIMESTAMP PRIMARY KEY);";
    private static final String dropTableSQL = "DROP TABLE IF EXISTS result";


    private static final String INSERT_TABLE_SQL = "INSERT INTO result (cat,dog,createTime)\n VALUES (?, ?, ?);";
    private static final String SELECT_BY_DATE_SQL = "SELECT * FROM result WHERE createTime < ?" +
            " ORDER BY createDate LIMIT 1;";

    private Connection connection = null;

    public ResultDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void create (Result result) throws SQLException, InsertionFailException {
        PreparedStatement insertPreparedStatement = this.connection.prepareStatement(INSERT_TABLE_SQL);
        insertPreparedStatement.setDouble(1,result.getCat());
        insertPreparedStatement.setDouble(2,result.getDog());
        insertPreparedStatement.setTimestamp(3, Timestamp.valueOf(result.getCreateTime()));
        Boolean executeStatementResult = insertPreparedStatement.execute();
        if(!executeStatementResult){
            throw new InsertionFailException();
        }
    }

    public Result findByDate (LocalDateTime dateTime) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_BY_DATE_SQL);
        preparedStatement.setTimestamp(1,Timestamp.valueOf(dateTime));
        ResultSet resultSet = preparedStatement.executeQuery();
        Boolean next = resultSet.next();
        Result result = null;
        if(next){
            result = new Result(1,1);
        }
//        Double cat = null;
//        Double dog = null;
//        Timestamp createTime = null;
//        try {
//            PreparedStatement preparedStatement = this.connection.prepareStatement(this.selectByDateSQL);
//            preparedStatement.execute();
//            ResultSet resultSet = preparedStatement.getResultSet();
//            resultSet.next();
//            resultSet.next();
//            cat = resultSet.getDouble(1);
//            dog = resultSet.getDouble(2);
//            createTime = resultSet.getTimestamp(3);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new Result(cat,dog, createTime.toLocalDateTime());
        return result;
    }
}
