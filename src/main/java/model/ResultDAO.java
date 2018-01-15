package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResultDAO {
    private Connection connection = null;

    public ResultDAO(Connection connection) throws SQLException {
        this.connection = connection;
        final PreparedStatement preparedStatement = this.connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS result\n(\n    cat INT,\n    dog INT,\n    createTime TIMESTAMP\n);");
        preparedStatement.execute();

    }

    public boolean create (Result result){
        String sqlCreate = "INSERT INTO result (cat,dog,createTime)\n" +
                " VALUES (?, ?, ?);";
        boolean success = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate);
            preparedStatement.setDouble(1,result.getCat());
            preparedStatement.setDouble(2,result.getDog());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(result.getCreateTime()));
            success = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Getter Setter

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
