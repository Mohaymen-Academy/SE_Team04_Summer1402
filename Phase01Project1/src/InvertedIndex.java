import Documents.Document;
import Normalizer.Normalizable;
import Stemmer.PorterStemmer;
import Tokenize.Tokenizable;

import java.security.InvalidParameterException;
import java.util.*;

public class InvertedIndex {

    private ArrayList<Document> documents;
    private HashMap<String, ArrayList<Document>> wordDocuments;
    private Normalizable tokenNormalization;
    private SearchQuery searchQuery;
    private PorterStemmer porterStemmer = new PorterStemmer();
    private Tokenizable tokenizer;

    public InvertedIndex(SearchQuery searchQuery, ArrayList<Document> documents, Tokenizable tokenizer){
        wordDocuments =  new HashMap<>();
        this.searchQuery = searchQuery;
        this.tokenNormalization = searchQuery.tokenNormalization;
        this.documents = documents;
        this.tokenizer = tokenizer;
    }

    public void fillWordDocument(String delimiter){
        if (!isValidDocument()){
            throw new InvalidParameterException();
        }
        for(Document document : documents){
            for(String documentWord : tokenizer.tokenize(document.getText(), delimiter)){
                documentWord = tokenNormalization.normalize(documentWord);
                documentWord = porterStemmer.stemWord(documentWord);
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
    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public Map<String, ArrayList<Document>> getWordDocuments() {
        return wordDocuments;
    }
}
