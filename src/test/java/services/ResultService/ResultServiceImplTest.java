package services.ResultService;

import model.result.Result;
import model.result.ResultDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;

import  static  org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultServiceImplTest {


    @Mock
    ResultDAO resultDAOMock;
    @InjectMocks
    ResultServiceImpl resultService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getResultByDateCallResultDAO() throws SQLException {
        // GIVEN

        LocalDate date = LocalDate.now();
        // WHEN
        Result result = resultService.getResultByDate(date);
        // THEN
        verify(resultDAOMock).findByDate(date);

    }

    @Test
    public void createResultCallCreateResultDAO() throws SQLException {
        // Given
        Result result = new Result(1,1);
        // WHEN
        resultService.createResult(result);
        // TEEN
        verify(resultDAOMock).create(result);
    }
}