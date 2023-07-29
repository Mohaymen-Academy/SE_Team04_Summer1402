import Documents.Document;
import java.security.InvalidParameterException;
import java.util.*;

public class EdgeNGram {

    private Map<String, ArrayList<Document>> edgeWordDocuments;
    public Map<String, ArrayList<Document>> wordDocuments;

    public EdgeNGram(Map<String, ArrayList<Document>> wordDocuments){
        this.wordDocuments = wordDocuments;
        edgeWordDocuments = new HashMap<>();
    }

    public void fillEdgeWordDocuments(int min, int max){
        for(String word : wordDocuments.keySet()){
            ArrayList<String> edgedWords = extractEdgeWords(word, min, max);
            if(!edgeWordDocuments.containsKey(word)){
                edgeWordDocuments.put(word, wordDocuments.get(word));
            }
            for(String edgedWord : edgedWords){
                if(edgeWordDocuments.containsKey(edgedWord)){
                    edgeWordDocuments.get(edgedWord).addAll(wordDocuments.get(word));
                }
                else {
                    edgeWordDocuments.put(edgedWord, wordDocuments.get(word));
                }
            }
        }
        for(String word : edgeWordDocuments.keySet()){
            Set<Document> uniqueDocumentsSet = new HashSet<>(edgeWordDocuments.get(word));
            ArrayList<Document> uniqueDocumentArray = new ArrayList<>(uniqueDocumentsSet);
            edgeWordDocuments.put(word, uniqueDocumentArray);
        }
    }

    public ArrayList<String> extractEdgeWords(String word, int min, int max){
        if(max < min || max < 1 || min < 1 || word == null){
            throw new InvalidParameterException();
        }
        ArrayList<String> result = new ArrayList<>();
        for(int wordLength = min; wordLength <= max; wordLength++){
            for(int startIndex = 0; startIndex < word.length() - wordLength + 1; startIndex++){
                String slicedString = word.substring(startIndex, startIndex+wordLength);
                if(!result.contains(slicedString))
                    result.add(slicedString);
            }
        }
        return result;
    }

    public Map<String, ArrayList<Document>> getEdgeWordDocuments() {
        return edgeWordDocuments;
    }
}
