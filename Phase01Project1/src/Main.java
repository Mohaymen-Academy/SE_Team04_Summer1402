import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private final static String folderPath = "/Users/hosseinb/Desktop/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
    private static HashSet<Document> result;
    private static InvertedIndex invertedIndex;
    private static String inputQuery;

    public static void preprocess(){
        invertedIndex =  new InvertedIndex();
        invertedIndex.extractDocument(folderPath);
    }

    public static void getInput(){
        System.out.println("Folder path : ");
        Scanner scanner = new Scanner(System.in);
        inputQuery = scanner.nextLine();
    }

    public static void setSearchResult(){
        result = invertedIndex.advancedSearch(inputQuery, " \n");
    }
    public static void printSearchResult(){
        for(Document doc : result){
            System.out.println(doc.getName());
        }
        if(result.isEmpty())
            System.out.println("No result found");
    }

    public static void main(String[] args) {
        preprocess();
        getInput();
        setSearchResult();
        printSearchResult();
    }
}