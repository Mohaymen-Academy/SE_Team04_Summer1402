import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {

    public static Map<String, String> books = FileReader.getFileDataWithFileName();
    public static Map<String, ArrayList<String>> words = new HashMap<>();
    public static void Search(String query){
        for(String bookName : books.keySet()) {
            String[] currentWords = books.get(bookName).split("[ \\n]", -1);
            for (String word : currentWords) {
                TokenNormalization tokenNormalization = new TokenNormalization();
                word = tokenNormalization.makeNormalizeAndStem(word);
                if (words.containsKey(word)) {
                    if (!words.get(word).contains(bookName)) {
                        words.get(word).add(bookName);
                    }
                } else {
                    words.put(word, new ArrayList<>(Arrays.asList(bookName)));
                }
            }
        }

        System.out.println(words.get(query));
    }


}
