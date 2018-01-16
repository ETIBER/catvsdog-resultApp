package scheduler;

import model.InsertionFailException;
import model.Result;
import model.ResultDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApiConnectionService;

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
    private ApiConnectionService apiConnectionServiceMock;
    @Mock
    private ResultDAO resultDAOMock;

    @Before
    public void SetUp() {
        importResultSchedulerImpl = new ImportResultSchedulerImpl(scheduledExecutorServiceMock, apiConnectionServiceMock,resultDAOMock);
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
    public void runCallGetResultInApi() {
        // GIVEN
        when(apiConnectionServiceMock.getResult()).thenReturn(new Result(1,0));

        // WHEN
        importResultSchedulerImpl.run();

        // THEN
        verify(apiConnectionServiceMock).getResult();
    }

    @Test
    public void runCorrectlyCallCreateWithResultFromApi() throws SQLException, InsertionFailException {
        // GIVEN
        Result expectedResult = new Result(1, 0);
        when(apiConnectionServiceMock.getResult()).thenReturn(expectedResult);

        // WHEN
        importResultSchedulerImpl.run();

        // THEN
        verify(resultDAOMock).create(eq(expectedResult));

    }
}