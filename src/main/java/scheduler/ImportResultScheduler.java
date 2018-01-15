package scheduler;

import model.Result;
import model.ResultDAO;
import services.ApiConnectionService;
import services.DataBaseConnectionService;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImportResultScheduler implements Runnable {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ImportResultScheduler() {
        scheduler.schedule(this,1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        Result result = new ApiConnectionService().getResult();
        System.out.println(result);
        try {
            ResultDAO resultDAO = new ResultDAO(new DataBaseConnectionService().getConnection());
            resultDAO.create(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
