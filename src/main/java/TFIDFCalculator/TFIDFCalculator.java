package TFIDFCalculator;

import Documents.Document;
import Normalizer.Normalizable;
import java.util.ArrayList;

public class TFIDFCalculator {
    private Normalizable normalizable;

    public TFIDFCalculator(Normalizable normalizable){
        this.normalizable = normalizable;
    }
    public double tf(Document doc, String term) {
        term = normalizable.normalize(term);
        double result = 0;
        if (doc.getWordCounter().containsKey(term))
            result = doc.getWordCounter().get(term);
        return result / doc.getTextWordQuantity();
    }
    public double idf(ArrayList<Document> docs, String term) {
        double n = 0;
        term = normalizable.normalize(term);
        for (Document doc : docs)
            if (doc.getWordCounter().containsKey(term))
                n++;
        return Math.log(docs.size() / n > 0 ? n : 1);
    }

    public double tfIdf(Document doc, ArrayList<Document> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }
}
