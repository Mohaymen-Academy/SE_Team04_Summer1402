package Documents;

import lombok.Getter;
import lombok.Setter;

public abstract class Document{

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String text;

    public Document(String name, String text){
        this.setName(name);
        this.setText(text);
    }

    public boolean hasWord(String word){
        return text.contains(word);
    }

    public boolean equals(Document document) {
        return (this.name.equals(document.name));
    }

}
