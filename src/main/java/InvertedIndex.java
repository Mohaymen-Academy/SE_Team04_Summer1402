import Documents.Document;
import Normalizer.Normalizable;
import Stemmer.PorterStemmer;
import Tokenize.Tokenizer;
import lombok.Getter;
import lombok.Setter;

import java.security.InvalidParameterException;
import java.util.*;

public class InvertedIndex {

    @Getter @Setter
    private ArrayList<Document> documents;
    @Getter
    private Map<String, ArrayList<Document>> wordDocuments;
    private Normalizable tokenNormalization;
    private PorterStemmer porterStemmer = new PorterStemmer();

    public InvertedIndex(Normalizable tokenNormalization, ArrayList<Document> documents){
        wordDocuments =  new HashMap<>();
        this.tokenNormalization = tokenNormalization;
        this.documents = documents;
    }

    public void fillWordDocument(String delimiter){
        if (!isValidDocument()){
            throw new InvalidParameterException();
        }
        Tokenizer tokenizer = new Tokenizer();
        for(Document document : documents){
            for(String documentWord : tokenizer.tokenize(document.getText(), delimiter)){
                documentWord = tokenNormalization.normalize(documentWord);
                //documentWord = porterStemmer.stemWord(documentWord);
                if(!wordDocuments.containsKey(documentWord))
                    wordDocuments.put(documentWord, new ArrayList<>());
                if(!wordDocuments.get(documentWord).contains(document))
                    wordDocuments.get(documentWord).add(document);
            }
        }
    }

    private boolean isValidDocument(){
        return (documents != null && !documents.isEmpty());
    }
}
