package services.ResultService;

import model.result.Result;
import model.result.ResultDAO;
import model.result.ResultDAOImpl;
import services.DatabaseConnectionService.DatabaseConnectionService;
import services.DatabaseConnectionService.DatabaseConnectionServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;

public class ResultServiceImpl implements ResultService {

private ResultDAO resultDAO;

    public ResultServiceImpl() throws SQLException {
        this.resultDAO = new ResultDAOImpl();
    }


    @Override
    public Result getResultByDate(LocalDate date) throws SQLException {
        return resultDAO.findByDate(date);
    }

    @Override
    public void createResult(Result result) throws SQLException {
        resultDAO.create(result);
    }
}
