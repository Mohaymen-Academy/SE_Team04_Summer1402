package main.java;

import TFIDFCalculator.TFIDFCalculator;
import main.java.Documents.Document;
import main.java.FileReaders.TxtFileReader;
import main.java.Normalizer.TokenNormalization;
import main.java.SearchFilters.AndSearchFilter;
import main.java.SearchFilters.NotSearchFilter;
import main.java.SearchFilters.OrSearchFilter;
import main.java.SearchQueryFilter.AndQueryHandler;
import main.java.SearchQueryFilter.NotQueryHandler;
import main.java.SearchQueryFilter.OrQueryHandler;
import main.java.Stemmer.PorterStemmer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private final static String folderPath = "/home/amirack/Code/Java/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
    private static ArrayList<Document> result = new ArrayList<>();
    private static String query;
    private static TokenNormalization normalizer;
    private static PorterStemmer stemmer;
    private static TFIDFCalculator tfidfCalculator;
    private static ArrayList<Document> documents;

    public static void getInput() {
        System.out.println("Search query : ");
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        if (inputQuery.equals("")) {
            System.out.println("No Input Query");
            System.exit(0);
        }
        query = inputQuery;
        normalizer = new TokenNormalization();
        stemmer = new PorterStemmer();
    }

    public static void setSearchResult(){
        TxtFileReader txtFileReader = new TxtFileReader();
        documents = txtFileReader.readFiles(folderPath);
        InvertedIndex invertedIndex = new InvertedIndex(normalizer, documents);
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
            try {
                andQueryHandler.handler(token);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.exit(0);
            }
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
        tfidfCalculator = new TFIDFCalculator(normalizer);
        tfIdfSort(
                highPriorityWords,
                lowPriorityWords
        );
    }
    public static void printSearchResult(){
        for(Document doc : result){
            System.out.println(doc.getName());
        }
        if(result.isEmpty())
            System.out.println("No result found");
    }

    public static void tfIdfSort(
            ArrayList<String> highPriorityWords,
            ArrayList<String> lowPriorityWords
    ){
        for (Document doc : Main.result){
            double tfidf = 0;
            for (String highPriorityWord : highPriorityWords){
                tfidf += tfidfCalculator.tf(doc,highPriorityWord,"\s\n") * 3;
            }
            for (String lowPriorityWord : lowPriorityWords){
                tfidf += tfidfCalculator.tfIdf(doc,Main.result,lowPriorityWord,"\s\n") * 2;
            }
            doc.setTfIdf(tfidf);
        }
        for (int i = 0 ; i < Main.result.size() ; i++){
            for(int j = i+1 ; j < Main.result.size() ; j++){
                if (Main.result.get(j).getTfIdf() > Main.result.get(i).getTfIdf()){
                    Collections.swap(Main.result, i,j);
                }
            }
        }
    }

    public static void main(String[] args) {
        getInput();
        setSearchResult();
        printSearchResult();
    }
}