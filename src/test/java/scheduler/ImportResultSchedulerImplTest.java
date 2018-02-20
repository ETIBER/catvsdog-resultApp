package scheduler;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApiConnectionService.ApiConnectionResultServiceImpl;
import services.ResultService.ResultService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImportResultSchedulerImplTest {


    @Mock
    private ScheduledExecutorService scheduledExecutorServiceMock;
    @Mock
    private ApiConnectionResultServiceImpl apiConnectionServiceImplMock;
    @Mock
    private ResultService resultServiceMock;

    @InjectMocks
    private ImportResultSchedulerImpl importResultSchedulerImpl;

    @Before
    public void SetUp() {
    }

    @Test
    public void executeShouldCorrectlyCallsScheduler() {
        // Given

        // When
        importResultSchedulerImpl.execute();

        // Then
        verify(scheduledExecutorServiceMock)
                .scheduleAtFixedRate(eq(importResultSchedulerImpl),eq(1L),eq(60L),eq(TimeUnit.MINUTES));
    }

    @Test
    public void runCallGetResultInApi() throws IOException {
        // GIVEN
        URLConnectionFactory urlConnectionFactory = new URLConnectionFactory();
        IOUtilsTools ioUtilsTools = new IOUtilsTools();
        when(apiConnectionServiceImplMock.getResult()).thenReturn(new Result(1,0));

        // WHEN
        importResultSchedulerImpl.run();

        // THEN
        verify(apiConnectionServiceImplMock).getResult();
    }

    @Test
    public void runCorrectlyCallCreateWithResultFromApi() throws SQLException, IOException {
        // GIVEN
        Result expectedResult = new Result(1, 0);
        when(apiConnectionServiceImplMock.getResult())
                .thenReturn(expectedResult);

        // WHEN
        importResultSchedulerImpl.run();

        // THEN
        verify(resultServiceMock).createResult(eq(expectedResult));

    }
}