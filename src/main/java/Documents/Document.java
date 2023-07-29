package Documents;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;

@Getter @Setter
public abstract class Document{
    private String name;
    private String text;
    private Long textWordQuantity;
    private HashMap<String,Long> wordCounter;

    public Document(String name, String text){
        this.setName(name);
        this.setText(text);
        wordCounter = new HashMap<>();
    }

    public boolean hasWord(String word){
        return text.contains(word);
    }

    public boolean equals(Document document) {
        return this.name.equals(document.name);
    }
}
