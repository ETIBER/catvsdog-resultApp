package scheduler;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import model.result.ResultDAO;
import services.ApiConnectionService.ApiConnectionServiceImpl;

import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImportResultSchedulerImpl implements ImportResultScheduler {

    private final ScheduledExecutorService scheduler;
    private final ApiConnectionServiceImpl apiConnectionServiceImpl;
    private final ResultDAO resultDAO;

    public ImportResultSchedulerImpl(ScheduledExecutorService scheduler, ApiConnectionServiceImpl apiConnectionServiceImplMock, ResultDAO resultDAO) {
        this.scheduler = scheduler;
        this.apiConnectionServiceImpl = apiConnectionServiceImplMock;
        this.resultDAO = resultDAO;
    }

    @Override
    public void run() {
        try {
            Result result = this.apiConnectionServiceImpl.getResult(new URLConnectionFactory(), new IOUtilsTools());
            this.resultDAO.create(result);
        } catch (SQLException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
    }
}
