import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private final static String folderPath = "/Users/hosseinb/Desktop/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
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
        invertedIndex.fillWordDocument(" \n");
        AndSearchFilter andSearchFilter = new AndSearchFilter(searchQuery.highPriorityWords, invertedIndex.getWordDocuments(), new ArrayList<>());
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