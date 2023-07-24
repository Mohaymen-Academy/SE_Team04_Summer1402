package main.java.SearchFilters;

import java.util.ArrayList;
import java.util.Map;
import main.java.Documents.Document;

public class NotSearchFilter extends SearchFilter {
    public NotSearchFilter(ArrayList<String> filterWords, Map<String, ArrayList<Document>> wordDocuments, ArrayList<Document> resultDocuments){
        super(filterWords, wordDocuments, resultDocuments);
    }

    @Override
    public ArrayList<Document> handleFilterWords() {
        ArrayList<Document>result = new ArrayList<>();
        for (String redPriorityWord : filterWords){
            if (wordDocuments.containsKey(redPriorityWord)){
                result.addAll(wordDocuments.get(redPriorityWord));
            }
        }
        return result;
    }

    @Override
    public ArrayList<Document> applyToResult() {
        for(Document documentToDelete : this.handleFilterWords()){
            resultDocuments.removeIf(resultDocument -> resultDocument.equals(documentToDelete));
        }
        return resultDocuments;
    }
}
