import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private final static String folderPath = "/home/amirack/Code/Java/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
    private static HashSet<Document> result = new HashSet<>();
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
        InvertedIndex invertedIndex = new InvertedIndex(searchQuery);
        invertedIndex.extractDocument(folderPath);
        try {
            result = invertedIndex.advancedSearch(" \n");
        }catch (Exception e){
            System.out.println("No Document preset");
        }
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