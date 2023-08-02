package Entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data @Builder
public class Subscription {

    private Long id;
    private Long userId;
    private Long chatId;
    private Instant subscribedAt;
}
