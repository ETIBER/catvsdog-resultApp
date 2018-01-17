package model.result;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ResultDAO {
    void create(Result result) throws SQLException;

    Result findClosestDate(LocalDateTime expectedDateTime) throws SQLException;

    Result findByDate(LocalDate expectedDate) throws SQLException;

}
