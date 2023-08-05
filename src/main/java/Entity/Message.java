package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "chat_id")
    private Long chatId;
    private String content;
    private String media;
    @Column(name = "view_count")
    private int viewCount;
    @Column(name = "sent_at")
    private Instant sentAt;

    public Message(){

    }

    public Message(Long userId, Long chatId, String content){
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
        this.sentAt = Instant.now();
        this.viewCount = 0;
    }
}
