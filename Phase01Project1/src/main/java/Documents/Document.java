package main.java.Documents;

public abstract class Document{
    private String name;
    private String text;
    private double tfIdf;

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

    public void setTfIdf(double tfIdf) {
        this.tfIdf = tfIdf;
    }

    public double getTfIdf() {
        return tfIdf;
    }
}
