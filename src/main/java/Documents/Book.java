package Documents;

import lombok.Getter;
import lombok.Setter;

public class Book extends Document {
    @Getter @Setter
    private double tfIdf;
    public Book(String name, String text) {
        super(name, text);
    }

}
