import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        String folderPath = "/Users/hosseinb/Desktop/SE_Team04_Summer1402/Phase01Project1/SoftwareBooksDataset";
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.extractDocument(folderPath);
        HashSet<Document> result;
        result = invertedIndex.advancedSearch(inputQuery, " \n");
        for(Document doc : result){
            System.out.println(doc.getName());
        }
    }
}