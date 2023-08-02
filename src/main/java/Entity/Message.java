package Entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data @Builder
public class Message {

    private Long id;
    private Long userId;
    private Long chatId;
    private String text;
    private String media;
    private Long viewCount;
    private Instant sentAt;
}
