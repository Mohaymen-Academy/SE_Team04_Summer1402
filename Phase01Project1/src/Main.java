import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        TokenNormalization tokenNormalization = new TokenNormalization();
        inputQuery = tokenNormalization.makeNormalizeAndStem(inputQuery);
        InvertedIndex.Search(inputQuery);
    }
}