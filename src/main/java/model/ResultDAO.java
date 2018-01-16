package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface ResultDAO {
    void create (Result result) throws SQLException, InsertionFailException;
    Result findByDate (LocalDateTime expectedDate) throws SQLException;

}
