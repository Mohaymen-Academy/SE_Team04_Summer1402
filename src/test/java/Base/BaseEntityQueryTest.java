package Base;

import Database.DatabaseConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseEntityQueryTest {
    private String url = "jdbc:postgresql://localhost:5432/";
    private String user = "postgres";
    private String password = "1234";
    protected DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    @BeforeEach
    public void setUp() {

        try(Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE mohaymenacademyTest";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Mockito.when(databaseConnector.getDBConnection()).thenReturn(connectToTestDB());
    }

    @AfterEach
    public void tearDown() {
        try(Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "DROP DATABASE mohaymenacademyTest";
            stmt.executeUpdate(sql);
            System.out.println("Database deleted successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Connection connectToTestDB(){
        try {
            System.out.println("Connected to the PostgresSQL server Test successfully.");
            return DriverManager.getConnection(url+"mohaymenacademyTest", user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
