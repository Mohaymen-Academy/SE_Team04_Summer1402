package Entity;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data @Builder
public class User {

    private Long id;
    private String userName;
    private Timestamp lastSeen;
    private String bio;
    private String phoneNumber;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String password;
}
