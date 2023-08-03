package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "subscribed_at")
    private Instant subscribedAt;

    public Subscription(){

    }

    public Subscription(Long userId, Long chatId){
        this.userId = userId;
        this.chatId = chatId;
        this.subscribedAt = Instant.now();
    }
}
