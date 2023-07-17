import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        InvertedIndex invertedIndex = new InvertedIndex();
        HashSet<String> result;
        result = invertedIndex.multiSearch(inputQuery);
        System.out.println(result);
    }
}