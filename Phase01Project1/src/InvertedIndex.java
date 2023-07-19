import javax.management.InvalidAttributeValueException;
import java.util.*;

public class InvertedIndex {

    private ArrayList<Document> documents;
    private Map<String, ArrayList<Document>> wordDocuments;
    private TokenNormalization tokenNormalization;
    private SearchQuery searchQuery;

    public InvertedIndex(SearchQuery searchQuery){
        documents = new ArrayList<>();
        wordDocuments =  new HashMap<>();
        this.searchQuery = searchQuery;
        this.tokenNormalization = searchQuery.tokenNormalization;
    }
    public void extractDocument(String folderPath) {
        this.documents = new TxtFileReader().readFiles(folderPath);
    }

    private void fillWordDocument(String delimiter){
        Tokenizer tokenizer = new Tokenizer();
        for(Document document : documents){
            for(String documentWord : tokenizer.tokenize(document.getText(), delimiter)){
                documentWord = tokenNormalization.makeNormalizeAndStem(documentWord);
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

    public HashSet<Document> advancedSearch(String delimiter) throws InvalidAttributeValueException {
        if (!isValidDocument()){
            throw new InvalidAttributeValueException();
        }
        fillWordDocument(delimiter);
        ArrayList<Document> result = new ArrayList<>();

        if(!searchQuery.highPriorityWords.isEmpty()){
            result = this.manageHighPriorityWords();
        }
        if(!searchQuery.lowPriorityWords.isEmpty()){
            result.retainAll(this.manageLowPriorityWords());
        }
        if(!searchQuery.redPriorityWords.isEmpty()){
            for(Document documentToDelete : this.manageRedPriorityWords()){
                result.removeIf(resultDocument -> resultDocument.equals(documentToDelete));
            }
        }
        return new HashSet<>(result);
    }

    private ArrayList<Document> manageHighPriorityWords(){
        ArrayList<Document> result = new ArrayList<>();
        if (!wordDocuments.containsKey(searchQuery.highPriorityWords.get(0))){
            return result;
        }
        result = wordDocuments.get(searchQuery.highPriorityWords.get(0));
        for (String highPriorityWord : searchQuery.highPriorityWords) {
            if (wordDocuments.containsKey(highPriorityWord)) {
                result.retainAll(wordDocuments.get(highPriorityWord));
            }
            else {
                return new ArrayList<>();
            }
        }
        return result;
    }

    private ArrayList<Document> manageLowPriorityWords(){
        ArrayList<Document>result = new ArrayList<>();
        for (String lowPriorityWord : searchQuery.lowPriorityWords){
            if (wordDocuments.containsKey(lowPriorityWord)){
                result.addAll(wordDocuments.get(lowPriorityWord));
            }
        }
        return result;
    }

    private ArrayList<Document> manageRedPriorityWords(){
        ArrayList<Document>result = new ArrayList<>();
        for (String redPriorityWord : searchQuery.redPriorityWords){
            if (wordDocuments.containsKey(redPriorityWord)){
                result.addAll(wordDocuments.get(redPriorityWord));
            }
        }
        return result;
    }


    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}
