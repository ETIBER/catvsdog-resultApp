package scheduler;

import model.result.Result;
import services.ApiConnectionService.ApiConnectionResultService;
import services.ApiConnectionService.ApiConnectionResultServiceImpl;
import services.ResultService.ResultService;
import services.ResultService.ResultServiceImpl;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImportResultSchedulerImpl implements ImportResultScheduler {

    private ScheduledExecutorService scheduler;
    private ApiConnectionResultService apiConnectionResultService;
    private ResultService resultService;

    public ImportResultSchedulerImpl() throws SQLException {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.apiConnectionResultService = new ApiConnectionResultServiceImpl();
        this.resultService = new ResultServiceImpl();
    }
    @Override
    public void run() {
        try {
            Result result = this.apiConnectionResultService.getResult();
            this.resultService.createResult(result);
        } catch (java.io.IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        scheduler.scheduleAtFixedRate(this, 1, 60, TimeUnit.MINUTES);
    }
}
