package main.java;

import main.java.Documents.Document;
import main.java.FileReaders.TxtFileReader;
import main.java.Normalizer.Normalizable;
import main.java.Normalizer.TokenNormalization;
import main.java.SearchFilters.AndSearchFilter;
import main.java.SearchFilters.NotSearchFilter;
import main.java.SearchFilters.OrSearchFilter;
import main.java.SearchQueryFilter.AndQueryHandler;
import main.java.SearchQueryFilter.NotQueryHandler;
import main.java.SearchQueryFilter.OrQueryHandler;
import main.java.Stemmer.PorterStemmer;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final static String folderPath = "/Users/hosseinb/Desktop/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
    private static ArrayList<Document> result = new ArrayList<>();
    private static SearchQuery searchQuery;
    private static String query;
    private static TokenNormalization normalizer;
    private static PorterStemmer stemmer;

    public static void getInput() {
        System.out.println("Search query : ");
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        if (inputQuery.equals("")) {
            System.out.println("No Input Query");
            System.exit(0);
        }
        searchQuery = new SearchQuery(inputQuery);
        query = inputQuery;
        normalizer = new TokenNormalization();
        stemmer = new PorterStemmer();
    }

    public static void setSearchResult(){
        TxtFileReader txtFileReader = new TxtFileReader();
        InvertedIndex invertedIndex = new InvertedIndex(searchQuery, txtFileReader.readFiles(folderPath));
        try {
            invertedIndex.fillWordDocument("\s\n");
        }catch (Exception e){
            System.out.println("No Document");
            System.exit(0);
        }
        AndQueryHandler andQueryHandler = new AndQueryHandler();
        OrQueryHandler orQueryHandler = new OrQueryHandler();
        NotQueryHandler notQueryHandler = new NotQueryHandler();
        andQueryHandler.setHandler(orQueryHandler).setHandler(notQueryHandler);
        for(String token : query.split(" ")){
            andQueryHandler.handle(token);
        }
        ArrayList<String> highPriorityWords = stemmer.stemArray(normalizer.normalizeArray(andQueryHandler.queries));
        ArrayList<String> lowPriorityWords = stemmer.stemArray(normalizer.normalizeArray(orQueryHandler.queries));
        ArrayList<String> redPriorityWords = stemmer.stemArray(normalizer.normalizeArray(notQueryHandler.queries));
        AndSearchFilter andSearchFilter = new AndSearchFilter(highPriorityWords, invertedIndex.getWordDocuments(), invertedIndex.getDocuments());
        result = andSearchFilter.applyToResult();
        OrSearchFilter orSearchFilter = new OrSearchFilter(lowPriorityWords, invertedIndex.getWordDocuments(), result);
        result = orSearchFilter.applyToResult();
        NotSearchFilter notSearchFilter = new NotSearchFilter(redPriorityWords, invertedIndex.getWordDocuments(), result);
        result = notSearchFilter.applyToResult();
    }
    public static void printSearchResult(){
        for(Document doc : result){
            System.out.println(doc.getName());
        }
        if(result.isEmpty())
            System.out.println("No result found");
    }

    public static void main(String[] args) {
        getInput();
        setSearchResult();
        printSearchResult();
    }
}