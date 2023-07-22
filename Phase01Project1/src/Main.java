import Documents.Document;
import FileReaders.TxtFileReader;
import SearchFilters.AndSearchFilter;
import SearchFilters.NotSearchFilter;
import SearchFilters.OrSearchFilter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private final static String folderPath = "/home/amirack/Code/Java/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
    private static ArrayList<Document> result = new ArrayList<>();
    private static SearchQuery searchQuery;

    public static void getInput() {
        System.out.println("Search query : ");
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        if (inputQuery.equals("")) {
            System.out.println("No Input Query");
            System.exit(0);
        }
        searchQuery = new SearchQuery(inputQuery);
    }

    public static void setSearchResult(){
        TxtFileReader txtFileReader = new TxtFileReader();
        InvertedIndex invertedIndex = new InvertedIndex(searchQuery, txtFileReader.readFiles(folderPath));
        try {
            invertedIndex.fillWordDocument(" \n");
        }catch (Exception e){
            System.out.println("No Document");
            System.exit(0);
        }
        AndSearchFilter andSearchFilter = new AndSearchFilter(searchQuery.highPriorityWords, invertedIndex.getWordDocuments(), invertedIndex.getDocuments());
        result = andSearchFilter.applyToResult();
        OrSearchFilter orSearchFilter = new OrSearchFilter(searchQuery.lowPriorityWords, invertedIndex.getWordDocuments(), result);
        result = orSearchFilter.applyToResult();
        NotSearchFilter notSearchFilter = new NotSearchFilter(searchQuery.redPriorityWords, invertedIndex.getWordDocuments(), result);
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