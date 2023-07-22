package Filters;

import java.util.ArrayList;
import java.util.Map;
import Documents.Document;

public abstract class Filter {

    protected ArrayList<String> filterWords ;
    protected Map<String, ArrayList<Document>> wordDocuments;
    protected ArrayList<Document> resultDocuments;
    public Filter(ArrayList<String> filterWords, Map<String, ArrayList<Document>> wordDocuments, ArrayList<Document> resultDocuments){
        this.filterWords = filterWords;
        this.wordDocuments = wordDocuments;
        this.resultDocuments = resultDocuments;
    }

    abstract public ArrayList<Document> handleFilterWords();
    abstract public ArrayList<Document> applyToResult();

}
