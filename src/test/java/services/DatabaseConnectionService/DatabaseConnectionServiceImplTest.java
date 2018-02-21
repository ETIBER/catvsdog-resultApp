package services.DatabaseConnectionService;

import Tools.IOUtilsTools;
import factories.URLConnectionFactory;
import model.result.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApiConnectionService.ApiConnectionResultServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseConnectionServiceImplTest {

    private static final String PG_HOST = System.getenv("POSTGRES_HOST") != null
            ? System.getenv("POSTGRES_HOST"):"localhost/result";
    private static final String PG_USER = System.getenv("POSTGRES_USER") != null
            ?System.getenv("POSTGRES_USER") : "admin";
    private static final String PG_PASSWORD = System.getenv("POSTGRES_PASSWORD") != null
            ?System.getenv("POSTGRES_PASSWORD"):"admin";

    @Mock
    private DatabaseConnectionDriverService databaseConnectionDriverService;
    @Mock
    private Properties properties;
    @InjectMocks
    private DatabaseConnectionServiceImpl databaseConnectionService;

    @Before
    public void setUp(){
    }

    @Test
    public void getConnectionWithGoodProperties() {
        // GIVEN

        // WHEN
        databaseConnectionService.getConnection();

        // THEN
        verify(properties).setProperty("user",PG_USER);
        verify(properties).setProperty("password",PG_PASSWORD);

    }
    @Test
    public void getConnectionToValidDataBase() throws SQLException {
        // GIVEN
        String url = String.format("jdbc:postgresql://%s", PG_HOST);
        // WHEN
        databaseConnectionService.getConnection();

        // THEN
        verify(databaseConnectionDriverService).getConnection(url,properties);

    }


}