package Database;

import Entity.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ChatQuery {

    public static void createTableIfNotExist(Connection connection){
        try (Statement stmt = connection.createStatement()) {
            String tableSql = "CREATE TABLE IF NOT EXISTS chats" +
                    "(chat_id SERIAL PRIMARY KEY," +
                    " chat_type INTEGER not null," +
                    " name VARCHAR (25), " +
                    " bio varchar(255)," +
                    " picture varchar(255))";
            stmt.execute(tableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addChat(Chat chat){
        Connection connection = DatabaseConnector.getDBConnection();
        createTableIfNotExist(connection);
        try{
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO chats(chat_type, name, bio, picture)" +
                            "VALUES (?, ?, ?, ?);"
            );
            stmt.setInt(1, chat.getChatType().getValue());
            stmt.setString(2, chat.getName());
            stmt.setString(3, chat.getBio());
            stmt.setString(4, chat.getPicture());
            stmt.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
