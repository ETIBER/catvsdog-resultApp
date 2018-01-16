package scheduler;

import model.InsertionFailException;
import model.Result;
import model.ResultDAO;
import services.ApiConnectionService;

import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImportResultSchedulerImpl implements ImportResultScheduler {

    private final ScheduledExecutorService scheduler ;
    private final ApiConnectionService apiConnectionService;
    private final ResultDAO resultDAO;

    public ImportResultSchedulerImpl(ScheduledExecutorService scheduler, ApiConnectionService apiConnectionServiceMock, ResultDAO resultDAO) {
        this.scheduler = scheduler;
        this.apiConnectionService = apiConnectionServiceMock;
        this.resultDAO = resultDAO;
    }

    @Override
    public void run() {
        Result result = this.apiConnectionService.getResult();
        try {
            this.resultDAO.create(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InsertionFailException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        scheduler.scheduleAtFixedRate(this,1,1,TimeUnit.SECONDS);
    }
}
