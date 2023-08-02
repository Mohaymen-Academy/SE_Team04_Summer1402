package Database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class DatabaseConnectionTest {

    @Test
    public void connection_shouldBeSuccessful(){
        Connection connection = DatabaseConnector.getDBConnection();
        Assertions.assertNotNull(connection);
    }

    @Test
    public void connection_shouldBeSingleton(){
        Connection connection = DatabaseConnector.getDBConnection();
        Connection connection2 = DatabaseConnector.getDBConnection();
        Assertions.assertNotNull(connection);
        Assertions.assertNotNull(connection2);
        Assertions.assertSame(connection, connection2);
    }
}
