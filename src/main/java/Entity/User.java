package Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;
    @Column(name = "last_seen")
    private Instant lastSeen;
    private String bio;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String password;

    public User(){

    }

    public User(String username, String phoneNumber, String firstName, String password){
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.password = password;
        this.lastSeen = Instant.now();
    }
}
