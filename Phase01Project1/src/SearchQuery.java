import java.util.ArrayList;

public class SearchQuery {
    public ArrayList<String> highPriorityWords = new ArrayList<>();
    public ArrayList<String> lowPriorityWords = new ArrayList<>();
    public ArrayList<String> redPriorityWords = new ArrayList<>();

    public TokenNormalization tokenNormalization;

    public SearchQuery(String query, TokenNormalization tokenNormalization){
        this.tokenNormalization = tokenNormalization;
        this.fillPriorityWordsList(query);
    }

    public SearchQuery(String  query){
        this.tokenNormalization = new TokenNormalization();
        this.fillPriorityWordsList(query);
    }
    private void fillPriorityWordsList(String query){
        for (String queryWord : query.split(" "))
        {
            switch (queryWord.charAt(0)) {
                case '+' -> lowPriorityWords.add(tokenNormalization.makeNormalizeAndStem(queryWord.substring(1)));
                case '-' -> redPriorityWords.add(tokenNormalization.makeNormalizeAndStem(queryWord.substring(1)));
                default -> highPriorityWords.add(tokenNormalization.makeNormalizeAndStem(queryWord));
            }
        }
    }
}
