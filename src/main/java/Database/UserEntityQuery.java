package Database;

import Entity.UserEntity;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserEntityQuery {
    private static void createTabelIfNotExist(Connection connection){
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

    public static void addNewUser(UserEntity userEntity){
        Connection connection = DatabaseConnector.getDBConnection();
        createTabelIfNotExist(connection);
        insertNewUser(userEntity, connection);
    }

    private static void insertNewUser(UserEntity userEntity , Connection connection){
        PreparedStatement stmt = null;
        try{
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

//    public static boolean authenticateUser(String username, String password){
//
//    }
}
