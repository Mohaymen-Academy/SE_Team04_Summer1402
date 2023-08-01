package Database;

import Entity.UserEntity;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class UserEntityQuery {
    private static void createTableIfNotExist(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            String tableSql = "CREATE TABLE IF NOT EXISTS users"
                    + "(user_id SERIAL PRIMARY KEY," +
                    " username varchar(50) not null," +
                    " last_seen timestamp not null," +
                    " bio varchar(255)," +
                    " phone_number varchar(15) not null unique," +
                    " profile_picture varchar(255)," +
                    " first_name varchar(25)," +
                    " last_name varchar(25)," +
                    " password varchar(255))";
            stmt.execute(tableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNewUser(UserEntity userEntity) {
        Connection connection = DatabaseConnector.getDBConnection();
        createTableIfNotExist(connection);
        insertNewUser(userEntity, connection);
    }

    private static void insertNewUser(UserEntity userEntity, Connection connection) {
        PreparedStatement stmt = null;
        try {
            String hashPassword = Hashing.sha256()
                    .hashString(userEntity.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            stmt = connection.prepareStatement("INSERT INTO users" +
                    " (username, last_seen, bio, phone_number, profile_picture, first_name, last_name, password) values"
                    + " (?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, userEntity.getUserName());
            stmt.setTimestamp(2, userEntity.getLastSeen());
            stmt.setString(3, userEntity.getBio());
            stmt.setString(4, userEntity.getPhoneNumber());
            stmt.setString(5, userEntity.getProfilePicture());
            stmt.setString(6, userEntity.getFirstName());
            stmt.setString(7, userEntity.getLastName());
            stmt.setString(8, hashPassword);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean authenticateUser(String username, String password) {
        Connection connection = DatabaseConnector.getDBConnection();
        String hashPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement selectSql = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            selectSql.setString(1,username);
            selectSql.setString(2,hashPassword);
            ResultSet resultSet = stmt.executeQuery(selectSql.toString());
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUserByUsername(String username, String password) {
        Connection connection = DatabaseConnector.getDBConnection();
        if (!authenticateUser(username, password)){
            System.out.println("Wrong name or password!");
            return;
        }
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement deleteSql = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            deleteSql.setString(1,username);
            stmt.execute(deleteSql.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUserBioByUsername(String username, String bio) {
        Connection connection = DatabaseConnector.getDBConnection();
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement updateSql = connection.prepareStatement("UPDATE users SET bio = ? WHERE username = ?");
            updateSql.setString(2,username);
            updateSql.setString(1,bio);
            stmt.execute(updateSql.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
