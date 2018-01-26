package services.ResultService;

import model.result.Result;

import java.sql.SQLException;
import java.time.LocalDate;

public interface ResultService {
    Result getResultByDate(LocalDate date) throws SQLException;
    void createResult(Result result) throws SQLException;
}
