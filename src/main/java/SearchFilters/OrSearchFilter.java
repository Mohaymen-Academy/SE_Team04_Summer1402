package SearchFilters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import Documents.Document;

public class OrSearchFilter extends SearchFilter {

    public OrSearchFilter(ArrayList<String> filterWords, Map<String, ArrayList<Document>> wordDocuments, ArrayList<Document> resultDocuments){
        super(filterWords, wordDocuments, resultDocuments);
    }
    @Override
    public ArrayList<Document> handleFilterWords() {
        ArrayList<Document>result = new ArrayList<>();
        for (String lowPriorityWord : filterWords){
            if (wordDocuments.containsKey(lowPriorityWord)){
                result.addAll(wordDocuments.get(lowPriorityWord));
            }
        }
        Set<Document> uniqueResult = new HashSet<>(result);
        return new ArrayList<>(uniqueResult);
    }

    @Override
    public ArrayList<Document> applyToResult() {
        ArrayList<Document> filterDocuments = this.handleFilterWords();
        if(filterDocuments.isEmpty())
            return resultDocuments;
        if(resultDocuments.isEmpty())
            return resultDocuments;
        resultDocuments.retainAll(filterDocuments);
        return resultDocuments;
    }
}
