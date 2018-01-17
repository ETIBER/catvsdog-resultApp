package services.DatabaseConnectionService;

import java.sql.Connection;

public interface DatabaseConnectionService {
    Connection getConnection();
}
