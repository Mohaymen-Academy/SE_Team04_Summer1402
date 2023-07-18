
public abstract class Document{
    private String name;
    private String text;

    public Document(String name, String text){
        this.setName(name);
        this.setText(text);
    }

    public boolean hasWord(String word){
        return text.contains(word);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
