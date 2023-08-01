package Entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class UserEntity {
    String userName;
    Timestamp lastSeen;
    String bio;
    String phoneNumber;
    String profilePicture;
    String firstName;
    String lastName;
    String password;

    public UserEntity(
            String userName,
            Timestamp timestamp,
            String bio,
            String phoneNumber,
            String profilePicture,
            String firstName,
            String lastName,
            String password
    ){
        this.userName = userName;
        this.lastSeen = timestamp;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
