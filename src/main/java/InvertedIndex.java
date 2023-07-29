import Documents.Document;
import Normalizer.Normalizable;
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
            long textWordQuantity = 0;
            for(String documentWord : tokenizer.tokenize(document.getText(), delimiter)){
                documentWord = tokenNormalization.normalize(documentWord);
                if(!wordDocuments.containsKey(documentWord)) {
                    wordDocuments.put(documentWord, new ArrayList<>());
                }
                if(!wordDocuments.get(documentWord).contains(document)) {
                    wordDocuments.get(documentWord).add(document);
                }
                this.updateDocumentWordCounter(documentWord,document);
                textWordQuantity++;
            }
            document.setTextWordQuantity(textWordQuantity);
        }
    }

    private boolean isValidDocument(){
        return (documents != null && !documents.isEmpty());
    }

    private void updateDocumentWordCounter(String word, Document document){
        if (document.getWordCounter().containsKey(word)) {
            long wordCount = document.getWordCounter().get(word) + 1;
            document.getWordCounter().put(word,wordCount);
        }else{
            document.getWordCounter().put(word,1L);
        }
    }
}
