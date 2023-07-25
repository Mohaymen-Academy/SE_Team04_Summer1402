package main.java;


import main.java.Documents.Document;

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
            if(!edgeWordDocuments.containsKey(word)){
                edgeWordDocuments.put(word, wordDocuments.get(word));
            }
            for(int wordLength = min; wordLength <= max; wordLength++){
                for(int startIndex = 0; startIndex < word.length() - wordLength + 1; startIndex++){
                    String slicedString = word.substring(startIndex, startIndex+wordLength);
                    if(slicedString.equals("desig")){
                        System.out.println("*" + word);
                    }
                    if(edgeWordDocuments.containsKey(slicedString)){
                        edgeWordDocuments.get(slicedString).addAll(wordDocuments.get(word));
                    }
                    else {
                        edgeWordDocuments.put(slicedString, wordDocuments.get(word));
                    }
                }
            }
        }
        for(String word : edgeWordDocuments.keySet()){
            Set<Document> uniqueDocumentsSet = new HashSet<>(edgeWordDocuments.get(word));
            ArrayList<Document> uniqueDocumentArray = new ArrayList<>(uniqueDocumentsSet);
            edgeWordDocuments.put(word, uniqueDocumentArray);
        }
    }

    public Map<String, ArrayList<Document>> getEdgeWordDocuments() {
        return edgeWordDocuments;
    }
}
