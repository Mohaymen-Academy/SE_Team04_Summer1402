package Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chats")
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;
    @Column(name = "chat_type")
    private ChatType charType;
    private String name;
    private String bio;
    private String picture;

    public Chat(){

    }

    public Chat(ChatType charType, String name){
        this.charType = charType;
        this.name = name;
    }
}
