package TFIDFCalculator;

import main.java.Documents.Document;
import main.java.Normalizer.Normalizable;
import main.java.Tokenize.Tokenizer;

import java.util.ArrayList;

public class TFIDFCalculator {
    private Tokenizer tokenizer;
    private Normalizable normalizable;

    public TFIDFCalculator(Normalizable normalizable){
        tokenizer = new Tokenizer();
        this.normalizable = normalizable;
    }
    public double tf(Document doc, String term, String delimiter) {
        term = normalizable.normalize(term);
        double result = 0 , size = 0;
        for (String word : tokenizer.tokenize(doc.getText(), delimiter)) {
            if (term.equals(normalizable.normalize(word))){
                result++;
            }
            size++;
        }
        return result / size;
    }
    public double idf(ArrayList<Document> docs, String term, String delimiter) {
        double n = 0;
        term = normalizable.normalize(term);
        for (Document doc : docs) {
            for (String word : tokenizer.tokenize(doc.getText(), delimiter)) {
                if (term.equals(normalizable.normalize(word))) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    public double tfIdf(Document doc, ArrayList<Document> docs, String term, String delimiter) {
        return tf(doc, term, delimiter) * idf(docs, term, delimiter);
    }
}
