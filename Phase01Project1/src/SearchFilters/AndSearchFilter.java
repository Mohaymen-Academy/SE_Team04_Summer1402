package SearchFilters;

import java.util.ArrayList;
import java.util.Map;
import Documents.Document;

public class AndSearchFilter extends SearchFilter {

    public AndSearchFilter(ArrayList<String> filterWords, Map<String, ArrayList<Document>> wordDocuments, ArrayList<Document> resultDocuments){
        super(filterWords, wordDocuments, resultDocuments);
    }
    @Override
    public ArrayList<Document> handleFilterWords() {
        ArrayList<Document> result = new ArrayList<>();
        if(filterWords.isEmpty())
            return result;
        if (!wordDocuments.containsKey(filterWords.get(0))){
            return result;
        }
        result = wordDocuments.get(filterWords.get(0));
        for (String highPriorityWord : filterWords) {
            if (wordDocuments.containsKey(highPriorityWord)) {
                result.retainAll(wordDocuments.get(highPriorityWord));
            }
            else {
                return new ArrayList<>();
            }
        }
        return result;
    }

    @Override
    public ArrayList<Document> applyToResult() {
        ArrayList<Document> filterDocuments = handleFilterWords();
        if(!filterDocuments.isEmpty())
            resultDocuments = filterDocuments;
        return resultDocuments;
    }
}
