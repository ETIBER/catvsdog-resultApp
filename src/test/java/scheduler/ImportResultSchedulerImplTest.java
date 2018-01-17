package scheduler;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import model.result.ResultDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApiConnectionService.ApiConnectionServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImportResultSchedulerImplTest {

    private ImportResultSchedulerImpl importResultSchedulerImpl;
    @Mock
    private ScheduledExecutorService scheduledExecutorServiceMock;
    @Mock
    private ApiConnectionServiceImpl apiConnectionServiceImplMock;
    @Mock
    private ResultDAO resultDAOMock;

    @Before
    public void SetUp() {
        importResultSchedulerImpl = new ImportResultSchedulerImpl(scheduledExecutorServiceMock, apiConnectionServiceImplMock,resultDAOMock);
    }

    @Test
    public void executeShouldCorrectlyCallsScheduler() {
        // Given

        // When
        importResultSchedulerImpl.execute();

        // Then
        verify(scheduledExecutorServiceMock)
                .scheduleAtFixedRate(eq(importResultSchedulerImpl),eq(1L),eq(1L),eq(TimeUnit.SECONDS));
    }

    @Test
    public void runCallGetResultInApi() throws IOException {
        // GIVEN
        URLConnectionFactory urlConnectionFactory = new URLConnectionFactory();
        IOUtilsTools ioUtilsTools = new IOUtilsTools();
        when(apiConnectionServiceImplMock.getResult(any(), any())).thenReturn(new Result(1,0));

        // WHEN
        importResultSchedulerImpl.run();

        // THEN
        verify(apiConnectionServiceImplMock).getResult(any(URLConnectionFactory.class), any(IOUtilsTools.class));
    }

    @Test
    public void runCorrectlyCallCreateWithResultFromApi() throws SQLException, IOException {
        // GIVEN
        Result expectedResult = new Result(1, 0);
        when(apiConnectionServiceImplMock.getResult(any(), any()))
                .thenReturn(expectedResult);

        // WHEN
        importResultSchedulerImpl.run();

        // THEN
        verify(resultDAOMock).create(eq(expectedResult));

    }
}