package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private String url = "jdbc:postgresql://localhost:5432/mohaymenacademy";
    private String user = "postgres";
    private String password = "1234";
    private static Connection conn = null;

    private DatabaseConnector() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getDBConnection(){
        if (conn == null){
            new DatabaseConnector();
        }
        return conn;
    }
}
