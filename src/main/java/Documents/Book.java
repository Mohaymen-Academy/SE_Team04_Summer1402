package Documents;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter @Setter
public class Book extends Document {
    private double tfIdf;

    public Book(String name, String text) {
        super(name, text);
    }

    public static Comparator<Book> tfIdfComparator = (b1, b2) -> {
        if (b1.getTfIdf() > b2.getTfIdf()) return -1;
        else if (b1.getTfIdf() == b2.getTfIdf()) return 0;
        return 1;
    };
}
