package Entity;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Chat {

    private Long id;
    private ChatType chatType;
    private String name;
    private String bio;
    private String picture;
}
