package TFIDFCalculator;

import Documents.Document;
import Normalizer.Normalizable;
import Tokenize.Tokenizer;

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
        String finalTerm = term;
        long result = tokenizer.tokenize(doc.getText(), delimiter).stream()
                .filter(word -> finalTerm.equals(normalizable.normalize(word)))
                .count();
        long size = tokenizer.tokenize(doc.getText(), delimiter).size();
        return (double) result / size;
    }

    public double idf(ArrayList<Document> docs, String term, String delimiter) {
        term = normalizable.normalize(term);
        String finalTerm = term;
        long n = docs.stream()
                .filter(doc -> tokenizer.tokenize(doc.getText(), delimiter).stream()
                        .anyMatch(word -> finalTerm.equals(normalizable.normalize(word))))
                .count();

        return Math.log((double) docs.size() / (n == 0 ? 1 : n));
    }

    public double tfIdf(Document doc, ArrayList<Document> docs, String term, String delimiter) {
        return tf(doc, term, delimiter) * idf(docs, term, delimiter);
    }
}
